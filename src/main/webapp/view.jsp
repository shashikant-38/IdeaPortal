<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.project.servlets.Idea" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Ideas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="style.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top">
  <div class="container-fluid">
    <a class="navbar-brand" href="index.jsp">Idea Portal</a>
    <div class="collapse navbar-collapse">
      <ul class="navbar-nav ms-auto">
        <li class="nav-item"><a class="nav-link" href="index.jsp">Home</a></li>
        <li class="nav-item"><a class="nav-link" href="ViewIdeasServlet">View Ideas</a></li>
        <li class="nav-item"><a class="nav-link" href="#ideaForm">Submit Idea</a></li>
      </ul>
    </div>
  </div>
</nav>
<div class="container" style="margin-top:80px;">
  <h2>Submitted Ideas</h2>
  <table class="table table-striped table-hover">
    <thead>
      <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Description</th>
        <th>Author</th>
        <th>Submission Date</th>
        <th>Actions</th>
      </tr>
    </thead>
    <tbody>
      <%
          List<Idea> ideas = (List<Idea>)request.getAttribute("ideas");
          if(ideas != null) {
              for(Idea idea : ideas) {
      %>
        <tr>
          <td><%= idea.getId() %></td>
          <td><%= idea.getTitle() %></td>
          <td><%= idea.getDescription() %></td>
          <td><%= idea.getAuthor() %></td>
          <td><%= idea.getSubmissionDate() %></td>
          <td>
            <!-- Like Button and Count -->
            <form action="LikeServlet" method="post" style="display:inline;">
              <input type="hidden" name="idea_id" value="<%= idea.getId() %>" />
              <button type="submit" class="btn btn-sm btn-outline-success">Like (<%= idea.getLikeCount() != null ? idea.getLikeCount() : 0 %>)</button>
            </form>
            <!-- Comment Button and Count -->
            <button type="button" class="btn btn-sm btn-outline-primary" data-bs-toggle="modal" data-bs-target="#commentModal<%= idea.getId() %>">
              Comment (<%= idea.getCommentCount() != null ? idea.getCommentCount() : 0 %>)
            </button>
            <!-- Comment Modal -->
            <div class="modal fade" id="commentModal<%= idea.getId() %>" tabindex="-1" aria-labelledby="commentModalLabel<%= idea.getId() %>" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="commentModalLabel<%= idea.getId() %>">Add Comment</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <form action="CommentServlet" method="post">
                    <div class="modal-body">
                      <input type="hidden" name="idea_id" value="<%= idea.getId() %>" />
                      <div class="mb-3">
                        <label for="comment_text<%= idea.getId() %>" class="form-label">Comment</label>
                        <textarea class="form-control" id="comment_text<%= idea.getId() %>" name="comment_text" rows="3" required></textarea>
                      </div>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                      <button type="submit" class="btn btn-primary">Submit Comment</button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </td>
        </tr>
      <%
              }
          }
      %>
    </tbody>
  </table>
</div>
<footer class="footer mt-auto py-3 bg-light fixed-bottom">
  <div class="container text-center">
    <span class="text-muted">&copy; 2024 Idea Submission Portal</span>
  </div>
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 