const likeButtons = document.querySelectorAll(".like-button");

likeButtons.forEach((button) => {
  const likeCountElement = button.querySelector(".like-count");
  let likeCount = 0;
  let clicked = false;

  button.addEventListener("click", () => {
    if (clicked) {
      likeCount--;
      button.classList.remove("clicked");
    } else {
      likeCount++;
      button.classList.add("clicked");
    }
    clicked = !clicked;
    likeCountElement.textContent = likeCount;
  });
});

const cards = document.querySelectorAll(".card");

cards.forEach((card) => {
  const titleElement = card.querySelector("h2");
  const summaryElement = card.querySelector("p");

  const title = titleElement.textContent;
  const summary = summaryElement.textContent;

  const maxLengthTitle = 15;
  const maxLengthSummary = 50;

  if (title.length > maxLengthTitle) {
    titleElement.textContent = title.substring(0, maxLengthTitle) + "...";
  }

  if (summary.length > maxLengthSummary) {
    summaryElement.textContent = summary.substring(0, maxLengthSummary) + "...";
  }
});

document.addEventListener("DOMContentLoaded", function () {
  const likeCountElements = document.querySelectorAll(".like-count");
  const cards = document.querySelectorAll(".card");
  const sortByLikesButton = document.getElementById("flexRadioDefault1");

  sortByLikesButton.addEventListener("change", function () {
    const sortedCards = Array.from(cards).sort(function (cardA, cardB) {
      const likeCountA = parseInt(
        cardA.querySelector(".like-count").textContent
      );
      const likeCountB = parseInt(
        cardB.querySelector(".like-count").textContent
      );

      return likeCountB - likeCountA;
    });

    sortedCards.forEach(function (card) {
      card.parentNode.removeChild(card);
    });

    sortedCards.forEach(function (card) {
      document.querySelector(".content").appendChild(card);
    });
  });
});
