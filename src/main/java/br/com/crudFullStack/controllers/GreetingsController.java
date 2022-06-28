package br.com.crudFullStack.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.crudFullStack.models.Usuario;
import br.com.crudFullStack.repositorys.UsuarioRepository;

@RestController
public class GreetingsController {


	@Autowired
	private UsuarioRepository repository;

	@RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String greetingText(@PathVariable String name) {
		return "Hello " + name + "!";
	}

	@GetMapping(value = "/listar-todos")
	@ResponseBody // Retorna os dados par ao coporto da resposta
	public ResponseEntity<List<Usuario>> listarTodos() {
		List<Usuario> usuarios = repository.findAll();
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);

	}
	
	@GetMapping(value = "/buscar-por-id")
	@ResponseBody // Retorna os dados par ao coporto da resposta
	public ResponseEntity<Usuario> buscarUsuarioId(@RequestParam(name = "idUser") Long idUser) {
		Usuario usuario = repository.findById(idUser).get();
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);

	}

	@GetMapping(value = "/buscar-por-nome")
	@ResponseBody // Retorna os dados par ao coporto da resposta
	public ResponseEntity<List<Usuario>> buscarUsuarioPorNome(@RequestParam(name = "nome") String nome) {
		List<Usuario> usuarios = repository.buscarPorNome(nome.trim().toUpperCase());
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);

	}
	
	@PostMapping(value = "novo-usuario")
	@ResponseBody
	public ResponseEntity<Usuario> novoUsuario(@RequestBody Usuario usuario) {
		Usuario user = repository.save(usuario);
		return new ResponseEntity<Usuario>(user,HttpStatus.CREATED);
	}
	
	@PutMapping(value = "alterar-usuario")
	@ResponseBody
	public ResponseEntity<?> alterarUsuario(@RequestBody Usuario usuario) {
		if(usuario.getId() == null) {
			return new ResponseEntity<String>("ID do usuário não informado!",HttpStatus.OK);
		}
		Usuario user = repository.saveAndFlush(usuario);
		return new ResponseEntity<Usuario>(user,HttpStatus.OK);
	}
	
	@DeleteMapping(value = "deletar-usuario")
	@ResponseBody
	public ResponseEntity<String> deletarUsario(@RequestParam(name = "idUser") Long idUser) {
		repository.deleteById(idUser);
		return new ResponseEntity<String>(
				String.format("Usuário do ID: %d deletado com sucesso!", idUser), HttpStatus.CREATED);
	}


}
