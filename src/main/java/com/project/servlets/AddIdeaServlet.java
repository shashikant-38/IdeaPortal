package com.project.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AddIdeaServlet")
public class AddIdeaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String submissionDate = request.getParameter("submission_date");

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO ideas (title, description, author_id, submission_date) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, title);
                pstmt.setString(2, description);
                pstmt.setInt(3, user.getId());
                pstmt.setString(4, submissionDate);
                pstmt.executeUpdate();
            }
            // Fetch all ideas after insertion
            java.util.List<Idea> ideas = new java.util.ArrayList<>();
            String fetchSql = "SELECT * FROM ideas";
            try (PreparedStatement fetchStmt = conn.prepareStatement(fetchSql)) {
                java.sql.ResultSet rs = fetchStmt.executeQuery();
                while (rs.next()) {
                    Idea idea = new Idea();
                    idea.setId(rs.getInt("id"));
                    idea.setTitle(rs.getString("title"));
                    idea.setDescription(rs.getString("description"));
                    idea.setAuthor(String.valueOf(rs.getInt("author_id"))); // or join with users table for name
                    idea.setSubmissionDate(rs.getDate("submission_date"));
                    ideas.add(idea);
                }
            }
            request.setAttribute("ideas", ideas);
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            request.setAttribute("error", "Failed to add idea: " + e.getMessage());
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            return;
        }
    }
} 