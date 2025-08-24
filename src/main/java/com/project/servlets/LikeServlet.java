package com.project.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LikeServlet")
public class LikeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        try {
            int userId = ((User) session.getAttribute("user")).getId();
            int ideaId = Integer.parseInt(request.getParameter("idea_id"));

            try (java.sql.Connection conn = DBUtil.getConnection()) {
                // Check if the user has already liked this idea
                String checkSql = "SELECT id FROM likes WHERE user_id = ? AND idea_id = ?";
                try (java.sql.PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                    checkStmt.setInt(1, userId);
                    checkStmt.setInt(2, ideaId);
                    if (checkStmt.executeQuery().next()) {
                        // User has already liked, so we do nothing or could implement "unlike"
                    } else {
                        // Insert a new like
                        String insertSql = "INSERT INTO likes (user_id, idea_id) VALUES (?, ?)";
                        try (java.sql.PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                            insertStmt.setInt(1, userId);
                            insertStmt.setInt(2, ideaId);
                            insertStmt.executeUpdate();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // After liking, fetch all ideas again and forward to the dashboard
        java.util.List<Idea> ideas = IdeaDAO.getAllIdeas();
        request.setAttribute("ideas", ideas);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
} 