<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Idea Submission Portal</title>
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
  <div class="card mx-auto" style="max-width: 500px;">
    <div class="card-header bg-primary text-white">Submit a New Idea</div>
    <div class="card-body">
      <form action="AddIdeaServlet" method="post" id="ideaForm">
        <div class="mb-3">
          <label for="title" class="form-label">Title</label>
          <input type="text" class="form-control" name="title" required>
        </div>
        <div class="mb-3">
          <label for="description" class="form-label">Description</label>
          <textarea class="form-control" name="description" rows="3" required></textarea>
        </div>
        <div class="mb-3">
          <label for="author" class="form-label">Author</label>
          <input type="text" class="form-control" name="author" required>
        </div>
        <div class="mb-3">
          <label for="submission_date" class="form-label">Submission Date</label>
          <input type="date" class="form-control" name="submission_date" required>
        </div>
        <button type="submit" class="btn btn-primary w-100">Submit Idea</button>
      </form>
    </div>
  </div>
</div>
<footer class="footer mt-auto py-3 bg-light fixed-bottom">
  <div class="container text-center">
    <span class="text-muted">&copy; 2025 Idea Submission Portal</span>
  </div>
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 