package com.project.servlets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class IdeaDAO {

    public static List<Idea> getAllIdeas() {
        List<Idea> ideas = new ArrayList<>();
        String sql = "SELECT i.id, i.title, i.description, u.name AS author_name, i.submission_date, " +
                     "COUNT(DISTINCT l.id) AS like_count, COUNT(DISTINCT c.id) AS comment_count " +
                     "FROM ideas i " +
                     "JOIN users u ON i.author_id = u.id " +
                     "LEFT JOIN likes l ON i.id = l.idea_id " +
                     "LEFT JOIN comments c ON i.id = c.idea_id " +
                     "GROUP BY i.id ORDER BY i.submission_date DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Idea idea = new Idea();
                idea.setId(rs.getInt("id"));
                idea.setTitle(rs.getString("title"));
                idea.setDescription(rs.getString("description"));
                idea.setAuthor(rs.getString("author_name"));
                idea.setSubmissionDate(rs.getDate("submission_date"));
                idea.setLikeCount(rs.getInt("like_count"));
                idea.setCommentCount(rs.getInt("comment_count"));
                ideas.add(idea);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ideas;
    }
} 