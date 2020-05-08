	package DAO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import Modelo.Usuario;

public class UsuarioDAO implements DAO<Usuario, Integer> {

    private List<Usuario> usuarios;

    private File file;
    private FileOutputStream fos;
    private ObjectOutputStream outputFile;
    private int maxId = 0;
    
    public int getMaxId() {
		return maxId;
	}

    public UsuarioDAO(String filename) throws IOException {
        usuarios = new ArrayList<Usuario>();
        file = new File(filename);
        if (file.exists())
            readFromFile();
    }

    @Override
    public void add(Usuario usuario) {
        usuarios.add(usuario);
        saveToFile();
    }

    @Override
    public Usuario get(Integer chave) {
        for (Usuario usuario : usuarios) {
            if (chave.intValue() == usuario.getId()) {
                return usuario;
            }
        }
        return null;

    }

    @Override
    public List<Usuario> getAll() {

        return usuarios;
    }

    @Override
    public void update(Usuario usuario) {
        int index = usuarios.indexOf(usuario);
        if (index != -1) {
            usuarios.set(index, usuario);
            saveToFile();
        }
    }

    @Override
    public void remove(Usuario usuario) {
        int index = usuarios.indexOf(usuario);
        if (index != -1) {
            usuarios.remove(index);
        }
        saveToFile();

    }

    private void readFromFile() {
        Usuario usuario;
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream inputFile = new ObjectInputStream(fis)) {

            while (fis.available() > 0) {
                usuario = (Usuario) inputFile.readObject();
                usuarios.add(usuario);
            }
        } catch (Exception e) {
            System.out.println("ERRO ao ler Usuario do disco!");
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try (FileOutputStream fos = new FileOutputStream(file, false);
             ObjectOutputStream outputFile = new ObjectOutputStream(fos)) {

            for (Usuario p : usuarios) {
                outputFile.writeObject(p);
            }
            outputFile.flush();
        } catch (Exception e) {
            System.out.println("ERRO ao gravar Usuario no disco!");
            e.printStackTrace();
        }

    }

    private void close() throws IOException {
        outputFile.close();
        fos.close();
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
    }
    
}
