package br.com.fiap.dao;

import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.model.Jogo;
import br.com.fiap.model.Plataforma;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JogoDao {

    private static final String SQL_INSERT = "INSERT INTO tb_jogo (cd_jogo, nm_jogo, ds_jogo, ds_plataforma, dt_lancamento, vl_jogo, st_multiplayer, dt_cadastro, img_url) VALUES (sq_tb_jogo.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT * FROM tb_jogo";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM tb_jogo WHERE cd_jogo = ?";
    private static final String SQL_UPDATE = "UPDATE tb_jogo SET nm_jogo = ?, ds_jogo = ?, ds_plataforma = ?, dt_lancamento = ?, vl_jogo = ?, st_multiplayer = ?, dt_atualizacao = ?, img_url = ? WHERE cd_jogo = ?";
    private static final String SQL_DELETE = "DELETE FROM tb_jogo WHERE cd_jogo = ?";
    private static final String SQL_SELECT_BY_NAME = "SELECT * FROM tb_jogo WHERE upper(nm_jogo) like upper(?)";

    private Connection connection;

    public JogoDao(Connection connection) {
        this.connection = connection;
    }

    // Método para cadastrar um jogo (CREATE) sem retorno de ID
    public void cadastrar(Jogo jogo) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SQL_INSERT, new String[] {"cd_jogo"})) {
            preencherStatementComJogo(stmt, jogo);
            stmt.executeUpdate();
            ResultSet resultSet = stmt.getGeneratedKeys();
            resultSet.next();
            jogo.setCodigo(resultSet.getInt(1));
        }
    }

    // Método para listar todos os jogos (READ)
    public List<Jogo> listar() throws SQLException {
        List<Jogo> jogos = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL)) {

            while (rs.next()) {
                Jogo jogo = parseJogo(rs);
                jogos.add(jogo);
            }
        }

        return jogos;
    }

    public List<Jogo> pesquisarPorNome(String nome) throws SQLException {
        List<Jogo> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_SELECT_BY_NAME)){
            stmt.setString(1, "%" + nome + "%");
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next())
                    lista.add(parseJogo(rs));
            }
        }
        return lista;
    }

    // Método para buscar um jogo por ID (READ), lança JogoNaoEncontradoException se não encontrar
    public Jogo buscarPorId(int id) throws SQLException, EntidadeNaoEncontradaException {
        try (PreparedStatement stmt = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return parseJogo(rs);
                } else {
                    throw new EntidadeNaoEncontradaException("Jogo com ID " + id + " não encontrado.");
                }
            }
        }
    }

    // Método para atualizar um jogo (UPDATE), lança JogoNaoEncontradoException se o ID não existir
    public void atualizar(Jogo jogo) throws SQLException, EntidadeNaoEncontradaException {
        try (PreparedStatement stmt = connection.prepareStatement(SQL_UPDATE)) {
            preencherStatementComJogo(stmt, jogo);
            stmt.setInt(9, jogo.getCodigo());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new EntidadeNaoEncontradaException("Não foi possível atualizar o jogo com ID " + jogo.getCodigo() + ". ID não encontrado.");
            }
        }
    }

    // Método para deletar um jogo (DELETE), lança JogoNaoEncontradoException se o ID não existir
    public void deletar(int id) throws SQLException, EntidadeNaoEncontradaException {
        try (PreparedStatement stmt = connection.prepareStatement(SQL_DELETE)) {
            stmt.setInt(1, id);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted == 0) {
                throw new EntidadeNaoEncontradaException("Não foi possível remover o jogo com ID " + id + ". ID não encontrado.");
            }
        }
    }

    // Método auxiliar para mapear ResultSet para Jogo
    private Jogo parseJogo(ResultSet rs) throws SQLException {
        Jogo jogo = new Jogo();
        jogo.setCodigo(rs.getInt("cd_jogo"));
        jogo.setNome(rs.getString("nm_jogo"));
        jogo.setDescricao(rs.getString("ds_jogo"));
        jogo.setPlataforma(Plataforma.valueOf(rs.getString("ds_plataforma")));
        jogo.setDataLancamento(rs.getDate("dt_lancamento").toLocalDate());
        jogo.setValor(rs.getDouble("vl_jogo"));
        jogo.setMultiplayer(rs.getString("st_multiplayer").equals("S"));
        jogo.setDataCadastro(rs.getTimestamp("dt_cadastro").toLocalDateTime());
        Timestamp dataAtualizacao = rs.getTimestamp("dt_atualizacao");
        if (dataAtualizacao != null) {
            jogo.setDataAtualizacao(dataAtualizacao.toLocalDateTime());
        }
        jogo.setImgUrl(rs.getString("img_url"));

        return jogo;
    }

    // Método auxiliar para preencher PreparedStatement com Jogo
    private void preencherStatementComJogo(PreparedStatement stmt, Jogo jogo) throws SQLException {
        stmt.setString(1, jogo.getNome());
        stmt.setString(2, jogo.getDescricao());
        stmt.setString(3, jogo.getPlataforma().name());
        stmt.setDate(4, Date.valueOf(jogo.getDataLancamento()));
        stmt.setDouble(5, jogo.getValor());
        stmt.setBoolean(6, jogo.getMultiplayer());
        stmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        stmt.setString(8, jogo.getImgUrl());
    }
}