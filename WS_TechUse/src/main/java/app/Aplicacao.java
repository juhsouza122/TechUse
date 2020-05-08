package app;

import static spark.Spark.get;
import static spark.Spark.post;

import java.io.IOException;
import DAO.UsuarioDAO;
import Modelo.Usuario;

public class Aplicacao {
    public static void main(String[] args) throws IOException {
        CorsFilter.apply();
    	UsuarioDAO userDAO = new UsuarioDAO("usuarios.bin");

        
        get("/users", "application/json", (request, response) -> {
        
            response.header("Content-Type","application/json");
            return userDAO.getAll() ;
        }, new JSONTransformer());
        
        post("/users","application/json", (request, response) -> {
        	
        	int id = Integer.parseInt(request.queryParams("id"));
        	String nome = request.queryParams("nome");
        	String cpf = request.queryParams("cpf");
        	String email = request.queryParams("email");
        	String telefone = request.queryParams("telefone");
        	
        	Usuario usuario = new Usuario(id, nome, cpf, email, telefone);
        	userDAO.add(usuario);
        	
            response.header("Content-Type","application/json");
            response.status(201);
            return usuario;
        }, new JSONTransformer());
        
    }
}
