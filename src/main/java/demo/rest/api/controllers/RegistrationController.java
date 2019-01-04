package demo.rest.api.controllers;

import demo.rest.api.entites.RegistrationEntity;
import demo.rest.api.repositories.RegistrationRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("registration")
public class RegistrationController {

    private RegistrationRepo rr = new RegistrationRepo();

    @GetMapping
    public ResponseEntity getAllRegistration(){
        return new ResponseEntity(rr.getDatabase(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity getARegistration(@PathVariable String id,@RequestBody Map<String,String> body)
    {
        String username = body.get("username");
        if (rr.findByUsername(username) == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(rr.findByUsername(username),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createRegistration(@RequestBody Map<String,String> body)
    {
        String username = body.get("username");
        String password = body.get("password");
        String fullname = body.get("fullname");
        RegistrationEntity re = new RegistrationEntity();
        re.setUsername(username);
        re.setFullname(fullname);
        re.setPassword(password);
        rr.createRegistration(re);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity updateRegsistration(@RequestBody Map<String, String> body, @PathVariable String id)
    {
        String password = body.get("password");
        String fullname = body.get("fullname");
        RegistrationEntity re = new RegistrationEntity();
        re.setFullname(fullname);
        re.setPassword(password);
        rr.updateResgistration(id,re);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity removeRegistration(@PathVariable String id)
    {
        if (rr.deleteRegsitration(id)) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("find/{n}/{j}")
    public ResponseEntity getUser(@PathVariable int n, @PathVariable String j){
        List<RegistrationEntity> list = rr.getUserByNameLenght(n);
        if(list.isEmpty()) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(list, HttpStatus.OK);
    }

}
