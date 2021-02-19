import http from "../http-common";

class LeaveService {

    getByYear(year) {
        return http.get(`/leaves/${year}`);
    }

    create(data) {
        return http.post(`/leaves`, data);
    }

    update(id, status) {
        return http.put(`/leaves/${id}?leaveStatus=${status}`);
    }

    getLeaveStatistics(year) {
        return http.get(`/leaveStatistics/${year}`)
    }
}

export default new LeaveService();