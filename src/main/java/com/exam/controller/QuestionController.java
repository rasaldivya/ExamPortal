package com.exam.controller;

import com.exam.entity.Question;
import com.exam.entity.Quiz;
import com.exam.model.exams.QuizModel;
import com.exam.repo.QuestionRepository;
import com.exam.services.QuestionService;
import com.exam.services.QuizService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/question")
@CrossOrigin("*")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;
    @Autowired
    private QuestionRepository questionRepository;

    //add question

    @PostMapping("/")
    public ResponseEntity<?> addQuestion(@RequestBody Question question)
    {
        System.out.println("hidifjsidho");
        System.out.println(question);
        return ResponseEntity.ok(this.questionService.addQuestion(question));
    }

    //	 upload questions by excel
    @PostMapping("/upload")
    public ResponseEntity<String> uploadQuestionsExcel(@RequestParam("file") MultipartFile file,
                                                       @RequestParam("trade") String trade) {
        try {

            questionService.uploadQuestions(file,trade);
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error uploading file");
        }
    }
    @PostMapping("/upload-file")
    public ResponseEntity<?> uploadQuestionImage(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("trade") String trade)
    {
        System.out.println("file Type...."+file.getContentType());
        System.out.println("file Name...."+file.getOriginalFilename());
        System.out.println("Trade of Ques."+trade);
        try {
            // file.transferTo(new File("D:\\demo\\examPortalAngularFrontEnd-master\\src\\assets\\question\\"+trade+"\\"+file.getOriginalFilename()));

            // for deployment
            file.transferTo(new File("C:\\exam\\frontend\\nginx\\html\\exam-front\\assets\\question\\"+trade+"\\"+file.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return ResponseEntity.ok("Success");
    }

    @PostMapping("/images")
    public ResponseEntity<?> uploadImages(@RequestParam("file") List<MultipartFile> files,
                                          @RequestParam("trade") String trade) {
        if (files.isEmpty()) {
            throw new RuntimeException("Images not selected");
        }

        // Process jsonData as needed

        try {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    byte[] bytes = file.getBytes();
                    String fileName = file.getOriginalFilename();
                    // String filePath = "D:\\demo\\examPortalAngularFrontEnd-master\\src\\assets\\question\\"+trade+"\\" + fileName; // Specify the path where you want to save the file
                    //For Deployment
                    String filePath = "C:\\exam\\frontend\\nginx\\html\\exam-front\\assets\\question\\"+trade+"\\" + fileName; // Specify the path where you want to save the file
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                    stream.write(bytes);
                    stream.close();
                }
            }
            return ResponseEntity.ok("Success");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Images are not uploaded");
        }
    }

    //update question
    @PutMapping("/")
    public ResponseEntity<?> updateQuestion(@RequestBody Question question)
    {
        return ResponseEntity.ok(this.questionService.updateQuestion(question));
    }

    //get quiz wise questions
    @GetMapping("/quiz/{qid}")
    public ResponseEntity<?> getAllQuestionsOfQuiz(@PathVariable("qid") Long qid)
    {
        QuizModel quiz=this.quizService.getQuiz(qid);
        Set<Question> questions=quiz.getQuestionsOfQuiz();
        int totalQuestions=Integer.parseInt(quiz.getNoOfQuestions());
        List list=new ArrayList(questions);
        if(list.size()>totalQuestions)
        {
            list=list.subList(0,totalQuestions+1);
        }
        Collections.shuffle(list);

        return ResponseEntity.ok(list);
    }

    // get all questions of specific quiz
    @GetMapping("/quiz/all/{qid}")
    public ResponseEntity<?> getAllQuestionsOfQuizAdmin(@PathVariable("qid") Long qid)
    {
        Quiz quiz=new Quiz();
        quiz.setQuizId(qid);
        Set<Question>list=this.questionService.getQuestionOfQuiz(quiz);

        return ResponseEntity.ok(list);
    }


    //get a single question
    @GetMapping("/{qid}")
    public Question getQuestion(@PathVariable("qid") Long qid)
    {
        return this.questionService.getQuestion(qid);
    }
    //get all questions
    @GetMapping("/")
    public ResponseEntity<?> getAllQuestions()
    {

        Set<Question>list=this.questionService.getQuestions();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/trade")
    public ResponseEntity<?> getAllQuestionsByTrade(@RequestParam(required = false) String trade)
    {

        List<Question>list= trade==null ? questionRepository.findAll(): questionRepository.findByTrade(trade);;

        return ResponseEntity.ok(list);
    }
    //delete a single question
    @DeleteMapping("{qid}")
    public void deleteQuestion(@PathVariable("qid") Long qid)
    {
        this.questionService.deleteQuestion(qid);
    }
    @DeleteMapping("/multiple")
    public void deleteQuestion(@RequestParam("qIds") List<Long> qIds)
    {
        System.out.println("Idds for delete : "+qIds);
//        this.questionService.deleteQuestion(qids);
    }


    @GetMapping("/topics")
    public ResponseEntity<List<String>> getAllTopics() {
        List<String> trades = questionRepository.findAllTopics();
        return ResponseEntity.ok(trades);
    }

    @GetMapping("/trade/{trade}")
    public ResponseEntity<List<Question>> getQuestionsByTrade(@PathVariable String trade) {
        List<Question> questions = questionRepository.findByTrade(trade);
        if (questions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(questions);
    }
    @GetMapping("/trade/{trade}/topics")
    public ResponseEntity<List<String>> getUniqueTopicsByTrade(@PathVariable String trade) {
        List<String> topics = questionRepository.findAllUniqueTopicsByTrade(trade);
        // if (topics.isEmpty()) {
        //     return ResponseEntity.notFound().build();
        // }
        return ResponseEntity.ok(topics);
    }


    @PostMapping("/saveall")
	public ResponseEntity<String> saveUsers(@RequestBody List<Question> questions) {
		try {
			questionService.saveQuestions(questions);
			return ResponseEntity.ok("Questions saved successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while saving users.");
		}
	}

@PostMapping("/read-excel-column")
public ResponseEntity<Map<String, Object>> readExcelColumn(
        @RequestParam("file") MultipartFile file
) {
    try {
        Workbook wb = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = wb.getSheetAt(0);
        List<String> expectedColumns = List.of("content","option1", "option2", "option3", "option4", "topic", "type", "answer", "image");

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

        List<Map<String, String>> invalidOptions = new ArrayList<>();
        List<Map<String, String>> invalidTopic = new ArrayList<>();
        List<Map<String, String>> invalidImages = new ArrayList<>();
        List<Map<String, String>> invalidTOF = new ArrayList<>();
        List<Map<String, String>> invalidTypes = new ArrayList<>();
        List<Map<String, String>> validQuestions = new ArrayList<>();

        boolean hasError = !missingColumns.isEmpty();
        int totalQuestions = 0;
        int validQuestionsCount = 0;


        if (!hasError) {
            // Read data
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next(); // Skip header row
            while (rowIterator.hasNext()) {
                boolean queshasError = false;

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
                if(rowMap.get("content").isEmpty()) {
                continue;
                }
            totalQuestions++;

                // Validate topic
                String topic = rowMap.get("topic");
                if (topic != null && !topic.matches("[a-zA-Z0-9_-]+")) {
                    invalidTopic.add(rowMap);
                    hasError = true;
                    queshasError=true;
                    continue; // Skip further validation for this row
                }

                // Validate question type and options
                String questionType = rowMap.get("type");
                if (questionType != null && !questionType.isEmpty()) {
                    switch (questionType) {
                        case "FNB":
                        case "MCQ":
                            if (!validateMCQOptions(rowMap)) {
                                invalidOptions.add(rowMap); // Add invalid question to the list
                                hasError = true;
                                queshasError=true;
                            }
                            break;
                        case "MCQimg":
                            if (!validateMCQImgOptions(rowMap)) {
                                invalidImages.add(rowMap); // Add invalid question to the list
                                hasError = true;
                                queshasError=true;
                            }
                            if (!validateMCQOptions(rowMap)) {
                                invalidOptions.add(rowMap); // Add invalid question to the list
                                hasError = true;
                                queshasError=true;
                            }
                            break;
                        case "TOF":
                            if (!validateTOFOptions(rowMap)) {
                                invalidTOF.add(rowMap); // Add invalid question to the list
                                hasError = true;
                                queshasError=true;
                            }
                            break;
                        default:
                            invalidTypes.add(rowMap);
                            hasError = true;
                            queshasError=true;
                            // Do nothing for other question types
                    }
                }else {
                    invalidTypes.add(rowMap);
                }
                if(!queshasError)
                {
                    validQuestions.add(rowMap);
                    validQuestionsCount++;

                }
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("hasError", hasError);
        response.put("missingColumns", missingColumns);
        response.put("invalidOptions", invalidOptions);
        response.put("invalidImages", invalidImages);
        response.put("invalidTOF", invalidTOF);
        response.put("invalidTopic", invalidTopic);
        response.put("invalidTypes", invalidTypes);
        response.put("totalQuestions", totalQuestions);
        response.put("validQuestionsCount", validQuestionsCount);
        response.put("validQuestions", validQuestions);
        response.put("invalidQuestionsCount", invalidOptions.size() + invalidImages.size() + invalidTOF.size() +invalidTypes.size() + invalidTopic.size());

        return ResponseEntity.ok(response);

    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

private boolean validateTOFOptions(Map<String, String> rowMap) {
    String answer = rowMap.get("answer");
    return answer != null && (answer.equalsIgnoreCase("true") || answer.equalsIgnoreCase("false"));
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

    private boolean validateMCQOptions(Map<String, String> rowMap) {
        String[] options = {"option1", "option2", "option3", "option4"};
        String answer = rowMap.get("answer");
        for (String option : options) {
            String optionValue = rowMap.get(option);
            if (optionValue != null && !optionValue.isEmpty()) {
                if (optionValue.equals(answer)) {
                    return true; // Answer matched with one of the options
                }
            }
        }
        return false; // Answer did not match with any option
    }

    private boolean validateMCQImgOptions(Map<String, String> rowMap) {
        String[] options = {"option1", "option2", "option3", "option4"};
        String answer = rowMap.get("answer");
        String image = rowMap.get("image");
        for (String option : options) {
            String optionValue = rowMap.get(option);
            if (optionValue == null || optionValue.isEmpty()) {
                return false; // Option is missing or empty
            }
            if (optionValue.equals(answer)) {
                if (image == null || image.isEmpty()) {
                    return false; // Image is missing or empty for MCQimg
                }
                return true; // Answer matched with one of the options and image is provided
            }
        }
        return false; // Answer did not match with any option
    }








    // @GetMapping("/questions-groupby-topic")
    // public ResponseEntity<Map<String, Map<String, Object>>> getGroupedQuestionsByTopicAndTypeWithCountAndQuestions22(
    //     @RequestParam(required = false) String trade
    // ) {
    //     List<Question> questions = trade==null ? questionRepository.findAll(): questionRepository.findByTrade(trade);

    //     Map<String, Map<String, Object>> groupedQuestionsWithCountAndQuestions = new HashMap<>();

    //     for (Question question : questions) {
    //         String topic = question.getTopic();
    //         String type = question.getType();

    //         groupedQuestionsWithCountAndQuestions.putIfAbsent(topic, new HashMap<>());

    //         // Increment count
    //         Map<String, Object> typeMap = groupedQuestionsWithCountAndQuestions.get(topic);
    //         typeMap.put("count", (int) typeMap.getOrDefault("count", 0) + 1); // Cast count to int

    //         // Add question to the list
    //         List<Question> questionList = (List<Question>) typeMap.computeIfAbsent(type, k -> new ArrayList<Question>());
    //         questionList.add(question);
    //         typeMap.put(type, questionList);
    //     }

    //     return ResponseEntity.ok(groupedQuestionsWithCountAndQuestions);
    // }



    // @PostMapping("/random-questions")
    // public ResponseEntity<Map<String, Map<String, List<Question>>>> getRandomQuestionsByTopicAndType(
    //         @RequestBody Map<String, Map<String, Integer>> query) {

    //     Map<String, Map<String, List<Question>>> randomQuestions = new HashMap<>();

    //     for (Map.Entry<String, Map<String, Integer>> entry : query.entrySet()) {
    //         String topic = entry.getKey();
    //         Map<String, Integer> typeCounts = entry.getValue();

    //         randomQuestions.put(topic, new HashMap<>());

    //         for (Map.Entry<String, Integer> typeEntry : typeCounts.entrySet()) {
    //             String type = typeEntry.getKey();
    //             int count = typeEntry.getValue();

    //             randomQuestions.get(topic).put(type, getRandomQuestions(topic, type, count));
    //         }
    //     }

    //     return ResponseEntity.ok(randomQuestions);
    // }

    // private List<Question> getRandomQuestions(String topic, String type, int count) {
    //     List<Question> questions = questionRepository.findByTopicAndType(topic, type);

    //     List<Question> randomQuestions = new ArrayList<>();
    //     Random random = new Random();

    //     int availableQuestions = Math.min(count, questions.size());

    //     for (int i = 0; i < availableQuestions; i++) {
    //         int randomIndex = random.nextInt(questions.size());
    //         randomQuestions.add(questions.remove(randomIndex));
    //     }

    //     return randomQuestions;
    // }

    // @PostMapping("/random-questions")
    // public ResponseEntity<Map<String, Map<String, List<Question>>>> getRandomQuestionsByTopicAndType(
    //         @RequestBody Map<String, Map<String, Integer>> query,
    //         @RequestParam(required = false) String trade) {

    //     Map<String, Map<String, List<Question>>> randomQuestions = new HashMap<>();

    //     for (Map.Entry<String, Map<String, Integer>> entry : query.entrySet()) {
    //         String topic = entry.getKey();
    //         Map<String, Integer> typeCounts = entry.getValue();

    //         randomQuestions.put(topic, new HashMap<>());

    //         for (Map.Entry<String, Integer> typeEntry : typeCounts.entrySet()) {
    //             String type = typeEntry.getKey();
    //             int count = typeEntry.getValue();

    //             randomQuestions.get(topic).put(type, getRandomQuestions(topic, type, count, trade));
    //         }
    //     }

    //     return ResponseEntity.ok(randomQuestions);
    // }

    // private List<Question> getRandomQuestions(String topic, String type, int count, String trade) {
    //     List<Question> questions;
    //     if (trade != null) {
    //         questions = questionRepository.findByTopicAndTypeAndTrade(topic, type, trade);
    //     } else {
    //         questions = questionRepository.findByTopicAndType(topic, type);
    //     }

    //     List<Question> randomQuestions = new ArrayList<>();
    //     Random random = new Random();

    //     int availableQuestions = Math.min(count, questions.size());

    //     for (int i = 0; i < availableQuestions; i++) {
    //         int randomIndex = random.nextInt(questions.size());
    //         randomQuestions.add(questions.remove(randomIndex));
    //     }

    //     return randomQuestions;
    // }

}
