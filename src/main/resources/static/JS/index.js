const myCarouselElement = document.querySelector('#carouselExampleInterval')

const carousel = new bootstrap.Carousel(myCarouselElement, {
  interval: 2000,
  touch: false
})