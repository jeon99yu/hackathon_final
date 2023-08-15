function displayPosts() {
  const postList = document.getElementById("postList");
  const storedPosts = localStorage.getItem("posts");

  if (storedPosts) {
    const posts = JSON.parse(storedPosts);
    postList.innerHTML = "";

    for (let i = 0; i < posts.length; i++) {
      const post = posts[i];
      const li = document.createElement("li");
      const postTextSpan = document.createElement("span");
      postTextSpan.textContent = post;
      li.appendChild(postTextSpan);
    }
  }
}

window.addEventListener("load", displayPosts);

function deletePost(index) {
  const storedPosts = localStorage.getItem("posts");
  if (storedPosts) {
    const posts = JSON.parse(storedPosts);
    posts.splice(index, 1);
    localStorage.setItem("posts", JSON.stringify(posts));
    displayPosts();
  }
}

function onPageLoad() {
  const urlParams = new URLSearchParams(window.location.search);
  const nickname = urlParams.get("nickname");

  // 가져온 nickname 값을 <p> 태그에 반영
  const nicknameTextElement = document.getElementById("nicknameText");
  if (nickname) {
    nicknameTextElement.textContent = nickname;
  } else {
    nicknameTextElement.textContent = "닉네임을 입력해주세요.";
  }

  displayPosts();
  displayComments(); // 댓글 목록도 표시
}

function addPost(content) {
  const currentDate = new Date();
  const timestamp = currentDate.toLocaleString();
  const truncatedContent = content.slice(0, 100);
  const displayedContent =
    content.length > 100 ? truncatedContent + "..." : truncatedContent;
  const post = `${displayedContent} - ${timestamp}`;

  posts.unshift(content);
  localStorage.setItem("posts", JSON.stringify(posts));
  displayPosts();
}

function displayPosts() {
  const postList = document.getElementById("postList");
  const storedPosts = localStorage.getItem("posts");

  if (storedPosts) {
    const posts = JSON.parse(storedPosts);
    postList.innerHTML = "";

    for (let i = 0; i < posts.length; i++) {
      const post = posts[i];
      const li = document.createElement("li");
      const postTextSpan = document.createElement("span");
      postTextSpan.textContent = post;
      li.appendChild(postTextSpan);

      const editButton = document.createElement("button");
      editButton.textContent = "수정";
      editButton.classList.add("edit-button");
      editButton.addEventListener("click", function () {
        showEditForm(i);
      });
      li.appendChild(editButton);

      const deleteButton = document.createElement("button");
      deleteButton.textContent = "삭제";
      deleteButton.classList.add("delete-button");
      deleteButton.addEventListener("click", function () {
        deletePost(i);
      });
      li.appendChild(deleteButton);

      postList.appendChild(li);
    }
  }
}
function displayComments() {
  const commentList = document.getElementById("commentList");
  const storedComments = localStorage.getItem("comments");

  if (storedComments) {
    const comments = JSON.parse(storedComments);
    commentList.innerHTML = "";

    for (let i = 0; i < comments.length; i++) {
      const comment = comments[i];
      const li = document.createElement("li");
      const commentTextSpan = document.createElement("span");
      commentTextSpan.textContent = comment;
      li.appendChild(commentTextSpan);
      commentList.appendChild(li);
    }
  }
}

// 글 수정 폼 보여주기
function showEditForm(index) {
  const editForm = document.getElementById("editForm");
  const editContent = document.getElementById("editContent");
  const storedPosts = localStorage.getItem("posts");

  if (storedPosts) {
    const posts = JSON.parse(storedPosts);
    const post = posts[index];
    editContent.value = post;
    editForm.style.display = "block";
    editSubmitBtn.addEventListener("click", function () {
      editPost(index, editContent.value);
      editForm.style.display = "none";
    });
  }
}

// 글 삭제 또는 수정 기능

//코멘트기능
function deleteComment(index) {
  const storedComments = localStorage.getItem("comments");

  if (storedComments) {
    const comments = JSON.parse(storedComments);
    comments.splice(index, 1);
    updateComments(comments);
  }
}

function updateComments(comments) {
  localStorage.setItem("comments", JSON.stringify(comments));
  displayComments();
}

// 글 수정 기능
function editPost(index, newContent) {
  const storedPosts = localStorage.getItem("posts");

  if (storedPosts) {
    const posts = JSON.parse(storedPosts);
    posts[index] = newContent;
    updatePosts(posts);
  }
}

window.addEventListener("load", onPageLoad);

/*  마이페이지에서 글 삭제가 안되는 관계로 제거 
// 게시물 개수 업데이트 함수
function updatePostCount() {
const postCount = document.getElementById('postCount');
const postList = document.getElementById('postList');
const numPosts = postList.childElementCount;
postCount.textContent = `(${numPosts})`;
}

// 댓글 개수 업데이트 함수
function updateCommentCount() {
const commentCount = document.getElementById('commentCount');
const commentList = document.getElementById('commentList');
const numComments = commentList.childElementCount;
commentCount.textContent = `(${numComments})`;
}

// 페이지가 로드될 때 실행되는 함수
function onPageLoad() {
displayPosts();
updatePostCount();
updateCommentCount();
}

function addPost(content) {
updatePostCount(); 
}
function deletePost(index) {
updatePostCount(); 
}
function addComment(content) {
updateCommentCount(); 
}
function deleteComment(index) {
updateCommentCount(); 
}

window.addEventListener('load', onPageLoad);
*/
