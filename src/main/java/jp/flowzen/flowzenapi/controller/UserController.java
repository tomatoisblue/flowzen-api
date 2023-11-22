// package jp.flowzen.flowzenapi.controller;


// import java.util.List;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestHeader;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import jp.flowzen.flowzenapi.entity.User;
// import jp.flowzen.flowzenapi.form.user.UserRegistrationForm;
// import jp.flowzen.flowzenapi.helper.AuthenticationHelper;
// import jp.flowzen.flowzenapi.service.user.UserLogoutService;
// import jp.flowzen.flowzenapi.service.user.UserSignupService;
// import jp.flowzen.flowzenapi.service.user.UserSearchService;

// import jakarta.validation.Valid;

// /**
//  * UserController
//  */
// @Controller
// public class UserController {

//   private UserSignupService userSignupService;
//   private UserLogoutService userLogoutService;
//   private UserSearchService userSearchService;
//   private AuthenticationHelper authenticationHelper;
//   // private Logger log = LoggerFactory.getLogger(AccountController.class);


//   public UserController(UserSignupService userSignupService,
//                         UserLogoutService userLogoutService,
//                         UserSearchService userSearchService,
//                         AuthenticationHelper authenticationHelper) {
//     this.userSignupService = userSignupService;
//     this.userLogoutService = userLogoutService;
//     this.userSearchService = userSearchService;
//     this.authenticationHelper = authenticationHelper;

//   }


//   @GetMapping("/")
//   public String showTop() {
//     return "redirect:/boards";
//   }


//   @GetMapping("/login")
//   public String showLoginPage() {
//     return authenticationHelper.redirectIfLoggedIn("redirect:/boards", "user/login");
//   }

//   @GetMapping("/signup")
//   public String showSignup(@ModelAttribute("form") UserRegistrationForm form) {
//     return authenticationHelper.redirectIfLoggedIn("redirect:/boards", "user/signup");
//   }

//   @PostMapping("/signup")
//   public String signup(@Valid @ModelAttribute("form") UserRegistrationForm form, BindingResult result, RedirectAttributes redirectAttributes) {
//     if (result.hasErrors()) {
//       return "user/signup";
//     }

//     // try {
//     //   userSignupService.signup(form.getUsername(), form.getEmail(), form.getPassword());
//     // } catch (Exception e) {
//     //   System.out.println("Singup Failed : " + e.getMessage());
//     //   return "user/signup";
//     // }
//     return "redirect:/login";
//   }

//   // @PostMapping("/logout")
//   // public String logout() {
//   //   try {
//   //     userLogoutService.logout(authenticationHelper.getAuthenticatedUserDetails().getUser().getId());
//   //     return "redirect:/login";
//   //   } catch (Exception e) {
//   //     System.out.println(e.getMessage());
//   //     return "redirect:/login";
//   //   }
//   // }

//   @GetMapping("/search")
//   public String searchUser(@RequestParam("query") String query, @RequestHeader(value = "referer", required = false) final String referer, Model model) {
//     // if (query.isEmpty()) {
//     //   return "redirect:" + referer;
//     // }

//     List<User> userList= userSearchService.searchUser(query);
//     model.addAttribute("userList", userList);
//     return "user/search-result";
//   }
// }


package jp.flowzen.flowzenapi.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.flowzen.flowzenapi.entity.User;
import jp.flowzen.flowzenapi.form.user.UserRegistrationForm;
import jp.flowzen.flowzenapi.helper.AuthenticationHelper;
import jp.flowzen.flowzenapi.jwt.JwtUtil;
import jp.flowzen.flowzenapi.service.user.UserDeleteService;
import jp.flowzen.flowzenapi.service.user.UserLogoutService;
import jp.flowzen.flowzenapi.service.user.UserSignupService;
import jp.flowzen.flowzenapi.service.user.UserSearchService;

import jakarta.validation.Valid;

/**
 * UserController
 */
@RestController
// @RequestMapping("/api/v1")
public class UserController {

  private UserSignupService userSignupService;
  // private UserLogoutService userLogoutService;
  private UserSearchService userSearchService;
  private UserDeleteService userDeleteService;
  private JwtUtil jwtUtil;
  // private AuthenticationHelper authenticationHelper;
  // private Logger log = LoggerFactory.getLogger(AccountController.class);


  public UserController(UserSearchService userSearchService,
                        UserSignupService userSignupService,
                        UserDeleteService userDeleteService,
                        JwtUtil jwtUtil) {
    this.userSignupService = userSignupService;
    // this.userLogoutService = userLogoutService;
    this.userSearchService = userSearchService;
    this.userDeleteService = userDeleteService;
    this.jwtUtil = jwtUtil;
    // this.authenticationHelper = authenticationHelper;

  }


  @GetMapping("/")
  public String showTop() {
    return "redirect:/boards";
  }



  @PostMapping("/login")
  public ResponseEntity<?> login() {
    System.out.println("POST /login requested");
    // System.out.println(form);

    // if (result.hasErrors()) {
    //   System.out.println("Binding Result Failed...");
    //   return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    // }

    // try {
    //   // userSignupService.signup(form.getUsername(), form.getEmail(), form.getPassword());
    //   //  temporary code
    //   Authentication auth = authenticationHelper.authenticate(form);
    //   authenticationHelper.setAuthentication(auth);
    // } catch (Exception e) {
    //   System.out.println("Singup Failed : " + e.getMessage());
    //   return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    // }
    return ResponseEntity.ok(HttpStatus.OK);
  }

  // @GetMapping("/signup")
  // public String showSignup(@ModelAttribute("form") UserRegistrationForm form) {
  //   return authenticationHelper.redirectIfLoggedIn("redirect:/boards", "user/signup");
  // }

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@Valid @RequestBody UserRegistrationForm form, BindingResult result) {
    System.out.println("POST /signup requested");
    System.out.println(form);


    if (result.hasErrors()) {
      System.out.println("Binding Result Failed...");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    try {
      userSignupService.signup(form.getUsername(), form.getEmail(), form.getPassword());
    } catch (Exception e) {
      System.out.println("Singup Failed : " + e.getMessage());
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return ResponseEntity.ok(HttpStatus.OK);
  }


  @DeleteMapping("/delete-account")
  public ResponseEntity<?> delete(@RequestHeader("Authorization") String token) {
    Long userId = jwtUtil.getUserId(token);
    try {
      userDeleteService.delete(userId);
      return ResponseEntity.ok().body(null);
    } catch(Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

  }

  // @PostMapping("/logout")
  // public String logout() {
  //   try {
  //     userLogoutService.logout(authenticationHelper.getAuthenticatedUserDetails().getUser().getId());
  //     return "redirect:/login";
  //   } catch (Exception e) {
  //     System.out.println(e.getMessage());
  //     return "redirect:/login";
  //   }
  // }

  @GetMapping("/search")
  public String searchUser(@RequestParam("query") String query, @RequestHeader(value = "referer", required = false) final String referer, Model model) {
    // if (query.isEmpty()) {
    //   return "redirect:" + referer;
    // }

    List<User> userList= userSearchService.searchUser(query);
    model.addAttribute("userList", userList);
    return "user/search-result";
  }

  @PostMapping("/verify-token")
  public ResponseEntity<?> verifyToken() {
    return ResponseEntity.ok().body(null);
  }
}