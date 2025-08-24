package com.project.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ViewIdeasServlet")
public class ViewIdeasServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        List<Idea> ideas = new ArrayList<>();
        
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM ideas WHERE author_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, user.getId());
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Idea idea = new Idea();
                        idea.setId(rs.getInt("id"));
                        idea.setTitle(rs.getString("title"));
                        idea.setDescription(rs.getString("description"));
                        idea.setAuthor(user.getName());
                        idea.setSubmissionDate(rs.getDate("submission_date"));
                        ideas.add(idea);
                    }
                }
            }
        } catch (Exception e) {
            request.setAttribute("error", "Failed to load ideas: " + e.getMessage());
        }
        
        request.setAttribute("ideas", ideas);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
} 