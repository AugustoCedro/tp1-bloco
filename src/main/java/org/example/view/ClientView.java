package org.example.view;




import org.example.model.Client;

import java.util.List;
import java.util.Map;

public class ClientView {

    public static String renderList(List<Client> clients) {
        StringBuilder html = new StringBuilder("""
                <!DOCTYPE html>
                <html lang="pt">
                <head>
                    <meta charset="UTF-8">
                    <title>Lista de Clientes</title>
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                </head>
                <body class="container mt-5">
                    <h1>Lista de Clientes</h1>
                    <a href="/clients/new" class="btn btn-primary mb-3">Adicionar Novo Cliente</a>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nome</th>
                                <th>Email</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                """);
        for (Client client : clients) {
            html.append(String.format("""
                    <tr>
                        <td>%d</td>
                        <td>%s</td>
                        <td>%s</td>
                        <td>
                            <a href="/clients/edit/%d" class="btn btn-sm btn-warning">Editar</a>
                            <form action="/clients/delete/%d" method="post" style="display:inline;">
                                <button type="submit" class="btn btn-sm btn-danger">Deletar</button>
                            </form>
                        </td>
                    </tr>
                    """, client.getId(), client.getName(), client.getEmail(), client.getId(), client.getId()));
        }
        html.append("""
                        </tbody>
                    </table>
                </body>
                </html>
                """);
        return html.toString();
    }

    public static String renderForm(Map<String, Object> model) {
        Object id = model.get("id");
        String action = id != null ? "/clients/edit/" + id : "/clients";
        String title = id != null ? "Editar Cliente" : "Novo Cliente";
        String name = (String) model.getOrDefault("name", "");
        String email = (String) model.getOrDefault("email", "");

        return String.format("""
                <!DOCTYPE html>
                <html lang="pt">
                <head>
                    <meta charset="UTF-8">
                    <title>%s</title>
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                </head>
                <body class="container mt-5">
                    <h1>%s</h1>
                    <form action="%s" method="post">
                        <div class="mb-3">
                            <label for="name" class="form-label">Nome</label>
                            <input type="text" class="form-control" id="name" name="name" value="%s" required>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="email" name="email" value="%s" required>
                        </div>
                        <button type="submit" class="btn btn-success">Salvar</button>
                        <a href="/clients" class="btn btn-secondary">Cancelar</a>
                    </form>
                </body>
                </html>
                """, title, title, action, name, email);
    }
}
