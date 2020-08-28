package br.com.alura.forum.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// --> Advice = "Adendo", informação conselho ou recomendação.

@RestControllerAdvice
public class ErroDeValidacaoHandler {
	
	@Autowired
	private MessageSource messageSource; // -> Internacionalização com "LocaleContextHolder"
	
	// -> Quando dá um erro de validação de formulário, que exceção que o Spring lança? 
	// --> Uma exception chamada MethodArgumentNotValidException
	
	// --> O status code padrão a ser devolvido será o 200, mas é possível 
	// --> modificá-lo com a anotação @ResponseStatus
	
	@ResponseStatus (code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeFormularioDto> handler(MethodArgumentNotValidException exception) {
		List<ErroDeFormularioDto> erros = new ArrayList<>();
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroDeFormularioDto dto = new ErroDeFormularioDto(e.getField(), mensagem);
			erros.add(dto);
		});
		
		return erros;
	}

}
