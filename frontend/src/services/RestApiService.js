import { http } from "../RestApi";

class RestApiService {
  constructor(endpoint) {
    this.endpoint = endpoint;
  }

  getById(id) {
    return http.get(this.endpoint + "/" + id).catch(function (error) {
      if (error.response) {
        console.log("error response data: ", error.response.data);
        console.log("error response status: ", error.response.status);
        console.log("error response headers: ", error.response.headers);
      } else if (error.request) {
        console.log("error request without response: ", error.request);
      } else {
        console.log("error request: ", error.message);
      }
      console.log(error.config);
    });
  }

  getAll() {
    return http.get(this.endpoint).catch(function (error) {
      if (error.response) {
        console.log("error response data: ", error.response.data);
        console.log("error response status: ", error.response.status);
        console.log("error response headers: ", error.response.headers);
      } else if (error.request) {
        console.log("error request without response: ", error.request);
      } else {
        console.log("error request: ", error.message);
      }
      console.log(error.config);
    });
  }

  create(body) {
    return http.post(this.endpoint, body);
  }

  update(id, body) {
    return http.put(this.endpoint + "/" + id, body);
  }

  deleteById(id) {
    return http.delete(this.endpoint + "/" + id);
  }
}

export default RestApiService;
