const BASE_URL = 'http://localhost:8090'

// User Endpoints

// Cart Endpoints
export const GET_ELEMENT_FROM_CART = `${BASE_URL}`
export const ADD_ELEMENT_TO_CART = `${BASE_URL}/api/addCart`
export const UPDATE_ELEMENT_IN_CART = `${BASE_URL}/api/updateCart`
export const MOVIE_EXIST_IN_CART = `${BASE_URL}/api/getCartsMovieid`
