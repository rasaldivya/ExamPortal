package com.exam.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.exam.model.exams.UserModel;
import com.exam.repo.UserRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exam.entity.User;
import com.exam.services.TraineeService;
import com.exam.services.UserService;

@RestController
@RequestMapping("/trainee")
@CrossOrigin("*")
public class TraineeController {

	@Autowired
	private TraineeService traineeService;
	
	private String defaultPassword="IndArmy@2024";

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

	@Autowired
	private UserRepository userRepository;

    @PostMapping("/")
    public UserModel addTrainee(@RequestBody UserModel userModel) throws Exception {
    	System.out.println("Trainee controller");
       userModel.setProfile("default.png");
       userModel.setPassword(bCryptPasswordEncoder.encode(defaultPassword));
       userModel.setUsername(userModel.getArmyNo());
        return  this.userService.addTrainee(userModel);
//        return null;
    }
//	 upload trainnes by excel
	 @PostMapping("/upload")
	 public ResponseEntity<?> uploadTraineExcel(@RequestParam("file") MultipartFile file) {
	        try {            
	        	
	        	traineeService.uploadTrainee(file);

	        	return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully");
//
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error uploading file");
	        }
	    }
	//get all Trainees
	    @GetMapping("/")
	    public ResponseEntity<?> getAllTrainees()
	    {
	    	System.out.println("get all trainee controller");

	      
	      List<User>list=this.traineeService.findByRole("NORMAL");

	        return ResponseEntity.ok(list);
	    }

	@GetMapping("/all")
	public ResponseEntity<List<User>> getUsersByTradeAndSquadAndRole(
			@RequestParam(name = "trade",required = false) String trade,
			@RequestParam(name = "squad",required = false) String squad,
			@RequestParam(name="role",required = false) String role) {


		if(role == null)
		{
			role="NORMAL";
		}
		List<User> users = userService.getUsersByTradeAndSquadAndRoleOrderByCreatedAt(trade, squad, role);

		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
	    //get a single Trainee
	    @GetMapping("/{tid}")
	    public User getTrainee(@PathVariable("tid") int tid)
	    {
	    	System.out.println("get single trainee");
	        return this.traineeService.getTrainee(tid);
	    }
	    //update trainee
	    @RequestMapping(method = RequestMethod.PUT,path = "/")
	    public ResponseEntity<?> updateTrainee(@RequestBody User trainee)
	    {
	    	System.out.println("Update trainee controller" );
	    	User user=traineeService.getTrainee(trainee.getId());
	    	user.setArmyNo(trainee.getArmyNo());
	    	user.setArmyRank(trainee.getArmyRank());
	    	user.setFirstName(trainee.getFirstName());
	    	user.setLastName(trainee.getLastName());
	    	user.setUnit(trainee.getUnit());
	    	user.setTrade(trainee.getTrade());
	    	user.setUserName(trainee.getArmyNo());
			user.setSquad(trainee.getSquad());

	        return ResponseEntity.ok(this.traineeService.updateTrainee(user));
	    }
	    //delete a single Trainee
	    @DeleteMapping("{id}")
	    public void deleteTrainee(@PathVariable("id") long id)
	    {
	    	System.out.println("delete trainee controller");
	        this.traineeService.deleteTrainee(id);
	    }


	@PostMapping("/read")
	public ResponseEntity<Map<String, Object>> readExcelColumn(
			@RequestParam("file") MultipartFile file
	) {
		try {
			Workbook wb = WorkbookFactory.create(file.getInputStream());
			Sheet sheet = wb.getSheetAt(0);
			List<String> expectedColumns = List.of("armyNo", "armyRank", "firstName", "lastName", "unit", "trade", "squad");
			List<String> expectedTrades = List.of("GNR", "DVR", "OPR", "OFC");


			
			// Get column names from header row
			Row headerRow = sheet.getRow(0);
			List<String> actualColumns = new ArrayList<>();
			if(headerRow!=null) {


				Iterator<Cell> headerCellIterator = headerRow.cellIterator();
				while (headerCellIterator.hasNext()) {
					Cell headerCell = headerCellIterator.next();
					actualColumns.add(headerCell.getStringCellValue().trim());
				}
			}

			List<String> missingColumns = new ArrayList<>(expectedColumns);
			missingColumns.removeAll(actualColumns);

			List<Map<String, String>> invalidTrade = new ArrayList<>();
			List<Map<String, String>> invalidSquad = new ArrayList<>();
			List<Map<String, String>> validTrainees = new ArrayList<>();
			List<Map<String, String>> invalidArmyNo = new ArrayList<>();
			List<Map<String, String>> duplicateArmyNo = new ArrayList<>();



			boolean hasError = !missingColumns.isEmpty();
			int totalTrainees = 0;
			int validTraineesCount = 0;


			if (!hasError) {
				// Read data
				Iterator<Row> rowIterator = sheet.iterator();
				rowIterator.next(); // Skip header row
				while (rowIterator.hasNext()) {
					boolean traineeshasError = false;

					Row row = rowIterator.next();
					Map<String, String> rowMap = new LinkedHashMap<>();

					for (int i = 0; i < actualColumns.size(); i++) {
						Cell cell = row.getCell(i);
						if (cell != null && expectedColumns.contains(actualColumns.get(i))) {
							String cellValue = getCellValueAsString(cell).trim();
							rowMap.put(actualColumns.get(i), cellValue);
						} else if( expectedColumns.contains(actualColumns.get(i))) {
							rowMap.put(actualColumns.get(i), "");
						}
					}
					if(rowMap.get("armyNo").isEmpty()) {
						continue;
					}
					totalTrainees++;

					String armyNo = rowMap.get("armyNo");
					if(userRepository.existsByArmyNo(armyNo))
					{
						duplicateArmyNo.add(rowMap);
						hasError = true;
						continue;
					}
					if (armyNo == null || !(armyNo.length() ==9)) {
						invalidArmyNo.add(rowMap);
						hasError = true;
						traineeshasError=true;
					}

					String lastName = rowMap.get("lastName");
					if (!validateString(lastName)) {
						rowMap.put("lastName","");
					}

					// Validate squad
					String squad = rowMap.get("squad");
					if (squad == null || !squad.matches("^[a-zA-Z0-9_-]+$")) {
						invalidSquad.add(rowMap);
						hasError = true;
						traineeshasError=true;
					}
					String trade = rowMap.get("trade");
					if (trade == null || !expectedTrades.contains(trade)) {
						invalidTrade.add(rowMap);
						hasError = true;
						traineeshasError=true;
					}


					
					if(!traineeshasError)
					{
						validTrainees.add(rowMap);
						validTraineesCount++;

					}
				}
			}

			Map<String, Object> response = new HashMap<>();
			response.put("hasError", hasError);
			response.put("missingColumns", missingColumns);
			response.put("invalidTrade", invalidTrade);
			response.put("invalidSquad", invalidSquad);
			response.put("invalidArmyNo", invalidArmyNo);
			response.put("duplicateArmyNo", duplicateArmyNo);
			response.put("totalTrainees", totalTrainees);
			response.put("validTraineesCount", validTraineesCount);
			response.put("validTrainees", validTrainees);
			response.put("invalidTraineesCount", invalidTrade.size() + invalidSquad.size() + invalidArmyNo.size());

			return ResponseEntity.ok(response);

		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	private String getCellValueAsString(Cell cell) {
		switch (cell.getCellType()) {
			case STRING:
				return cell.getStringCellValue();
			case NUMERIC:
				return String.valueOf(cell.getNumericCellValue());
			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			case FORMULA:
				// Handle formula cells
				return cell.getCellFormula();
			default:
				return "";
		}
	}

	public  boolean validateString(String input) {
        // Regular expression to check if the string contains at least one alphabet or number
        String regex = ".*[a-zA-Z0-9].*";
        
        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex);
        
        // Create a Matcher object
        Matcher matcher = pattern.matcher(input);
        
        // If at least one alphabet or number is found, return the original string, otherwise return an empty string
        if (!matcher.matches()) {
            return false;
        } else {
            return true;
        }
    }

	@GetMapping("/time")
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
	@PostMapping("/saveall")
	public ResponseEntity<String> saveUsers(@RequestBody List<User> userDTOs) {
		try {
			userService.saveUsers(userDTOs);
			return ResponseEntity.ok("Users saved successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while saving users.");
		}
	}
	
}
