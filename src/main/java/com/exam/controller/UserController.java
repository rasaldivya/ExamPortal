package com.exam.controller;

import com.exam.model.exams.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.exam.entity.User;
import com.exam.services.UserService;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
	
	private String defaultPassword="IndArmy@2024";

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;
    
    @PostMapping("/")
    public User addUser(@RequestBody User user) throws Exception {
       user.setProfile("default.png");
       user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
       user.setUserName(user.getArmyNo());
        return  this.userService.addUser(user);
//       return null;
    }
    @GetMapping("/admin/default")
    public User addUserAdmin() throws Exception {

        User user = new User();
        user.setProfile("default.png");
        user.setFirstName("Admin");
        user.setLastName("Admin");
        user.setArmyNo("ADMIN1001");
        user.setPassword(bCryptPasswordEncoder.encode("admin"));
        user.setUserName("ADMIN1001");
        user.setArmyRank("NA");
        user.setUnit("NA");
        user.setTrade("NA");
        return  this.userService.addUser(user);
//       return null;
    }

    @PostMapping("/trainee")
    public UserModel addTrainee(@RequestBody UserModel userModel) throws Exception {
       userModel.setProfile("default.png");
       userModel.setPassword(bCryptPasswordEncoder.encode(defaultPassword));
       userModel.setUsername(userModel.getArmyNo());
        return  this.userService.addTrainee(userModel);
    }

    @GetMapping("/{userName}")
    public User getUser(@PathVariable("userName") String uname)
    {
        System.out.println(uname);
        return this.userService.getUser(uname);
    }
    @GetMapping("/admin")
    public ResponseEntity<?> getAllAdmins()
    {
        System.out.println("get all admin controller");


        List<User>list=this.userService.findByRole("ADMIN");

        return ResponseEntity.ok(list);
    }
    @GetMapping("/admin/{userId}")
    public User getUserById(@PathVariable("userId") Long userId)
    {
        return this.userService.getUserById(userId);
    }
    @RequestMapping(method = RequestMethod.PUT,path = "/admin")
    public ResponseEntity<?> updateAdmin(@RequestBody User user) throws Exception
    {
        System.out.println("Update Admin controller");
        User admin=userService.getUserById(user.getId());
        admin.setArmyRank(user.getArmyRank());
        admin.setFirstName(user.getFirstName());
        admin.setLastName(user.getLastName());
        admin.setUnit(user.getUnit());
        admin.setTrade(user.getTrade());
        admin.setUserName(user.getArmyNo());
        System.out.println("Updated User name : "+admin.getUsername());
        if(!admin.getPassword().equals(user.getPassword())){
            admin.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        return ResponseEntity.ok(this.userService.updateUser(admin));

    }
    @GetMapping("/trainee/{armyNo}")
    public User getTrainee(@PathVariable("armyNo") String armyNo)
    {
        System.out.println("Login For Trainee"+armyNo);
      //  System.out.println(this.userService.getUserByArmyNo(armyNo));
        return this.userService.getUserByArmyNo(armyNo);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long uid)
    {
        System.out.println(uid);
         this.userService.deleteUser(uid);
    }

    @PatchMapping("/{userId}/trade")
    public ResponseEntity<?> updateTrade(@PathVariable Long userId, @RequestParam String trade) {
        userService.updateTrade(userId, trade);
        return ResponseEntity.status(HttpStatus.OK).body("Trade updated successfully");
    }

    // Endpoint to update squad
    @PatchMapping("/{userId}/squad")
    public ResponseEntity<String> updateSquad(@PathVariable Long userId, @RequestParam String squad) {
        userService.updateSquad(userId, squad);
        return ResponseEntity.status(HttpStatus.OK).body("Squad updated successfully");
    }

    @GetMapping("/squads")
    public ResponseEntity<Set<String>> getAllUniqueSquads() {
        Set<String> squads = userService.getAllUniqueSquads();
        squads.removeIf(Objects::isNull);
        return ResponseEntity.status(HttpStatus.OK).body(squads);
    }
    @GetMapping("/squads/trade")
    public ResponseEntity<Set<String>> getAllUniqueSquadsFilteredByTrade(@RequestParam(required = false) String trade) {
        Set<String> squads = userService.getAllUniqueSquadsFilteredByTrade(trade);
        squads.removeIf(Objects::isNull);
        return ResponseEntity.status(HttpStatus.OK).body(squads);
    }
    @GetMapping("/trades")
    public ResponseEntity<Set<String>> getAllUniqueTrades() {
        Set<String> trades = userService.getAllUniqueTrades();
        return ResponseEntity.status(HttpStatus.OK).body(trades);
    }


}
