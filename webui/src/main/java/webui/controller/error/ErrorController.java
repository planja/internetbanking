package webui.controller.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * Created by admin on 04.11.2016.
 */

@ControllerAdvice
public class ErrorController {

    /**
     * Глобальный перехват exception
     *
     * @param response
     * @param ex
     * @return
     * @throws Exception
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
        ModelAndView model = new ModelAndView("error/errorPage");
        ex.printStackTrace();
        model.addObject("error", ex.getMessage());
        return model;
    }

}
