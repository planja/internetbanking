package webui.config;


import infrastructure.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by admin on 25.10.2016.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        // Users in memory.

        //auth.inMemoryAuthentication().withUser("user1").password("12345").roles("USER");
        //auth.inMemoryAuthentication().withUser("admin1").password("12345").roles("USER, ADMIN");

        // For User in database.
        auth.userDetailsService(userDetailsService);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        // The pages does not require login
        http.authorizeRequests().antMatchers(
                "/",
                "/welcome",
                "/login",
                "/logout",
                "/registration",
                "/addUser"
        ).permitAll();

        // /userInfo page requires login as USER or ADMIN.
        // If no login, it will redirect to /login page.
        http.authorizeRequests().antMatchers(
                "/userInfo",
                "/updateUser",
                "/getUserInvoices",
                "/deleteInvoice/**",
                "/updateInvoice",
                "/saveInvoice",
                "/createPayment",
                "/payment",
                "/getInvoicesInfo",
                "/getPaymentTypesInfo",
                "/getMobileOperatorsInfo",
                "/getInternetOperatorsInfo",
                "/getPaymentStatusInfo",
                "/getPayments",
                "/transfer",
                "/getTransfer",
                "/createTransfer"
        ).access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_OPERATOR')");


        // For ADMIN only.
        http.authorizeRequests().antMatchers(
                "/admin",
                "/getRolesInfo",
                "/findAllUser",
                "/deleteUser/**",
                "/updateRoles",
                "/saveUserByAdmin",
                "/services",
                "/getServices",
                "/saveService",
                "/updateService",
                "/deleteService/**"
        ).access("hasRole('ROLE_ADMIN')");

        http.authorizeRequests().antMatchers(
                "/invoice",
                "/getUserInvoicesForNonUser",
                "/updateInvoiceForNonUser",
                "/deleteInvoiceForNonUser/**"
        ).access("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')");

        // When the user has logged in as XX.
        // But access a page that requires role YY,
        // AccessDeniedException will throw.
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

        // Config for Login Form
        http.authorizeRequests().and().formLogin()//
                // Submit URL of login page.
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/login")//
                .defaultSuccessUrl("/")//
                .failureUrl("/?error=true")//
                .usernameParameter("username")//
                .passwordParameter("password")
                // Config for Logout Page
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/");

    }
}