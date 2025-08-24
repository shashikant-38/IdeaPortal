package com.project.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int userId = ((User) session.getAttribute("user")).getId();
            int ideaId = Integer.parseInt(request.getParameter("idea_id"));
            String commentText = request.getParameter("comment_text");

            if (commentText != null && !commentText.trim().isEmpty()) {
                try (java.sql.Connection conn = DBUtil.getConnection()) {
                    String sql = "INSERT INTO comments (comment_text, user_id, idea_id) VALUES (?, ?, ?)";
                    try (java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, commentText);
                        pstmt.setInt(2, userId);
                        pstmt.setInt(3, ideaId);
                        pstmt.executeUpdate();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // After commenting, fetch all ideas again and forward to the dashboard
        java.util.List<Idea> ideas = IdeaDAO.getAllIdeas();
        request.setAttribute("ideas", ideas);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
} 