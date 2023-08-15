const writeButton = document.getElementById("writeButton");
const modal = document.getElementById("modal");
const modalContent = document.querySelector(".modal-content");

// 댓글과 글 데이터를 로컬 스토리지에서 가져오는 함수
function getStoredData() {
  const storedPosts = JSON.parse(localStorage.getItem("posts")) || [];
  return storedPosts;
}

// 모든 글을 로컬 스토리지에 저장하는 함수
function savePostsToStorage(posts) {
  localStorage.setItem("posts", JSON.stringify(posts));
}

// 로컬 스토리지에서 불러온 글을 화면에 렌더링하는 함수
function renderPosts() {
  const postsContainer = document.getElementById("posts");
  const storedPosts = getStoredData();
  storedPosts.forEach((postData) => {
    const postElement = createPostElement(postData);
    postsContainer.insertBefore(postElement, postsContainer.firstChild);
  });
}

// 글 생성 HTML 요소를 반환하는 함수
function createPostElement(postData) {
  const currentDate = new Date(postData.date);
  const formattedDate = `${currentDate.getFullYear()}년 ${
    currentDate.getMonth() + 1
  }월 ${currentDate.getDate()}일 ${currentDate.getHours()}시 ${currentDate.getMinutes()}분`;

  const postElement = document.createElement("div");
  postElement.classList.add("post");
  postElement.innerHTML = `
    <div class="post-header">
      <div class="post-title">${postData.title}</div>
      <div class="post-actions">
        <button class="editButton">수정</button>
        <button class="deleteButton">삭제</button>
        <button class="viewButton">자세히보기</button>
      </div>
    </div>
    <div class="post-content">${
      postData.content.length > 800
        ? postData.content.substring(0, 800) + "..."
        : postData.content
    }</div>
    <div class="post-date">${formattedDate}</div>
  `;
  return postElement;
}

// 초기 로딩 시 저장된 글 불러와서 렌더링
document.addEventListener("DOMContentLoaded", () => {
  const storedPosts = getStoredData();
  const sortedPosts = storedPosts.sort(
    (a, b) => new Date(b.date) - new Date(a.date)
  ); // 새로 추가한 부분
  const postsContainer = document.getElementById("posts");

  sortedPosts.forEach((postData) => {
    const postElement = createPostElement(postData);
    postsContainer.insertBefore(postElement, postsContainer.firstChild);
  });
});

// 모달 열기
writeButton.addEventListener("click", () => {
  modal.style.display = "block";
  modalContent.innerHTML = `
    <h3>글쓰기</h3>
    <form id="postForm">
      <label for="title">제목:</label><br>
      <input type="text" id="title" name="title" placeholder="글 제목을 입력하세요 (35자 제한)" maxlength="35" required><br>
      <label for="content">내용:</label><br>
      <textarea id="content" name="content" placeholder="내용을 자유롭게 입력하세요 (800자 제한)" maxlength="800" required></textarea><br>
      <button type="submit">글쓰기</button>
    </form>
  `;
});

// 게시물 작성 폼 제출 처리
modalContent.addEventListener("submit", (event) => {
  event.preventDefault();
  const title = event.target.title.value;
  const content = event.target.content.value;
  if (title && content) {
    const currentDate = new Date();
    const newPostData = {
      title,
      content,
      date: currentDate.toISOString(),
    };

    const storedPosts = getStoredData();
    storedPosts.unshift(newPostData);
    savePostsToStorage(storedPosts);

    const postElement = createPostElement(newPostData);
    const postsContainer = document.getElementById("posts");
    postsContainer.insertBefore(postElement, postsContainer.firstChild);
    modal.style.display = "none";
  }
});

// 모달 닫기
window.addEventListener("click", (event) => {
  if (event.target === modal) {
    modal.style.display = "none";
  }
});

// 수정 버튼 클릭 처리
document.addEventListener("click", (event) => {
  if (event.target.classList.contains("editButton")) {
    const post = event.target.closest(".post");
    const postTitle = post.querySelector(".post-title").textContent;
    const postContent = post.querySelector(".post-content").textContent;

    modal.style.display = "block";
    modalContent.innerHTML = `
      <h3>글 수정</h3>
      <form id="editForm">
        <label for="editTitle">제목:</label><br>
        <input type="text" id="editTitle" name="editTitle" value="${postTitle}" required><br>
        <label for="editContent">내용:</label><br>
        <textarea id="editContent" name="editContent" required>${postContent}</textarea><br>
        <button type="submit">수정 완료</button>
      </form>
    `;

    // 수정 폼 제출 처리
    modalContent.querySelector("#editForm").addEventListener("submit", (e) => {
      e.preventDefault();
      const newTitle = e.target.editTitle.value;
      const newContent = e.target.editContent.value;
      if (newTitle && newContent) {
        post.querySelector(".post-title").textContent = newTitle;
        post.querySelector(".post-content").textContent = newContent;
        modal.style.display = "none";
      }
    });
  }
});

// 삭제 버튼 클릭 처리
document.addEventListener("click", (event) => {
  if (event.target.classList.contains("deleteButton")) {
    const post = event.target.closest(".post");
    post.remove();
  }
});

// 글 자세히보기 클릭 처리 - 댓글달기 기능포함!
document.addEventListener("click", (event) => {
  if (event.target.classList.contains("viewButton")) {
    const post = event.target.closest(".post");
    const postTitle = post.querySelector(".post-title").textContent;
    const postContent = post.querySelector(".post-content").textContent;
    const comments = post.querySelectorAll(".comment-content");

    modal.style.display = "block";
    modalContent.innerHTML = `
      <h3>글 보기</h3>
      <div class="post-view">
        <div class="post-view-title">${postTitle}</div>
        <div class="post-view-content">${postContent}</div>
      </div>
      <h4>달린 댓글</h4>
      <div class="comments">
        ${Array.from(comments)
          .map(
            (comment) => `
          <div class="comment">
            <div class="comment-content" style="font-size: 14px;">${comment.textContent}</div>
            <div class="comment-actions">
              <button class="editCommentButton">댓글수정</button>
              <button class="deleteCommentButton">댓글삭제</button>
            </div>
          </div>
        `
          )
          .join("")}
      </div>
      <button class="addCommentButton">댓글 달기</button>
    `;
  }
});

// 댓글 작성 버튼 클릭 처리
document.addEventListener("click", (event) => {
  if (event.target.classList.contains("addCommentButton")) {
    const addCommentForm = `
      <form class="comment-form">
        <label for="comment">댓글:</label><br>
        <textarea id="comment" name="comment" placeholder="댓글을 작성하세요" style="height: 60px;" required></textarea>
        <br>
        <button type="submit">댓글 작성</button>
      </form>
    `;

    const comments = modalContent.querySelector(".comments");
    const commentFormExists = comments.querySelector(".comment-form");
    if (!commentFormExists) {
      const commentFormElement = document.createElement("div");
      commentFormElement.innerHTML = addCommentForm;

      comments.insertBefore(commentFormElement, comments.firstChild);
    }
  }
});

// 댓글 작성 폼 제출 처리
document.addEventListener("submit", (event) => {
  if (event.target.classList.contains("comment-form")) {
    event.preventDefault();
    const commentContent = event.target.querySelector("#comment").value;
    if (commentContent) {
      const currentDate = new Date();
      const formattedDate = `${currentDate.getFullYear()}년 ${
        currentDate.getMonth() + 1
      }월 ${currentDate.getDate()}일 ${currentDate.getHours()}시 ${currentDate.getMinutes()}분`;

      const commentElement = document.createElement("div");
      commentElement.classList.add("comment");
      commentElement.innerHTML = `
        <div class="comment-content" style="font-size: 14px;">${commentContent}</div>
        <div class="comment-actions">
          <button class="editCommentButton">댓글수정</button>
          <button class="deleteCommentButton">댓글삭제</button>
          <div class="comment-date">${formattedDate}</div>
        </div>
      `;

      // 수정 버튼 클릭 처리
      commentElement
        .querySelector(".editCommentButton")
        .addEventListener("click", () => {
          const commentContentElement =
            commentElement.querySelector(".comment-content");
          const commentText = commentContentElement.textContent;

          modal.style.display = "block";
          modalContent.innerHTML = `
          <h3>댓글 수정</h3>
          <form id="editCommentForm">
            <textarea id="editComment" name="editComment" >${commentText}</textarea><br>
            <button type="submit">수정 완료</button>
          </form>
        `;

          // 수정 폼 제출 처리
          modalContent
            .querySelector("#editCommentForm")
            .addEventListener("submit", (e) => {
              e.preventDefault();
              const newCommentContent = e.target.editComment.value;
              if (newCommentContent) {
                commentContentElement.textContent = newCommentContent;
                modal.style.display = "none";
              }
            });
        });

      // 댓글 삭제 버튼 클릭 처리
      commentElement
        .querySelector(".deleteCommentButton")
        .addEventListener("click", () => {
          const post = commentElement.closest(".post");
          commentElement.remove();
        });

      // 댓글 추가
      event.target.parentNode.insertBefore(commentElement, event.target);
      event.target.reset();
    }
  }
});

// 모든 삭제 버튼 클릭 처리
document.addEventListener("click", (event) => {
  if (event.target.classList.contains("deleteCommentButton")) {
    const commentElement = event.target.closest(".comment");
    const postView = modalContent.querySelector(".post-view");
    commentElement.remove();
  }
});
