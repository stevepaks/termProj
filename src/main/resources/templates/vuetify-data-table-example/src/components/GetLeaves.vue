
<template>
    <v-row align="center" class="list px-auto mx-auto">

        <v-col cols="12" lg="12">
            <v-card-title>Leave Statistics</v-card-title>
        </v-col>
        <v-col cols="12" lg="12">
            <v-card class="mx-auto">
                <v-data-table :headers="leaveStatisticHeaders" :items="leaveStatistics" disable-pagination :hide-default-footer="true">
                </v-data-table>
            </v-card>
        </v-col>

        <div class="col-md-8">
            <v-col cols="12" lg="12">
                <v-card-title>Leaves</v-card-title>
                <v-calendar ref="calendar"
                            color="primary"
                            :events="events"
                ></v-calendar>
            </v-col>
            <v-col cols="12" lg="12">
                <v-card class="mx-auto">
                    <v-data-table :headers="leaveHeaders" :items="leaves" disable-pagination :hide-default-footer="true">
                        <template v-slot:[`item.actions`]="{ item }">
                            <v-icon small class="mr-2" @click="approveLeave(item.id)">mdi-play</v-icon>
                            <v-icon small @click="cancelLeave(item.id)">mdi-stop</v-icon>
                        </template>
                    </v-data-table>
                </v-card>
            </v-col>
        </div>

        <div class="col-md-1">
        </div>

        <div class="col-md-3">
            <v-col cols="12" lg="12">
                <v-card-title>Add Leave</v-card-title>
            </v-col>
            <v-form ref="form" lazy-validation>
                <v-select
                        v-model="leave.employeeId"
                        :rules="[(v) => !!v || 'Employee Id is required']"
                        label="Employee Id"
                        :items="['K0001']"
                ></v-select>

                <v-date-picker
                        v-model="leave.dates" range
                        :rules="[(v) => !!v || 'Star and End are required']"
                        required
                ></v-date-picker>

                <v-checkbox
                        v-model="leave.isHalfDay"
                        label="IsHalfDay"
                ></v-checkbox>
            </v-form>

            <v-btn color="primary" class="mt-3" @click="saveLeave">Submit</v-btn>
        </div>
    </v-row>
</template>
<script src='https://unpkg.com/v-calendar'></script>
<script>
    import LeaveService from "../services/LeaveService";
    export default {
        name: "leave-list",
        data() {
            return {
                leave: {
                    employeeId: "",
                    dates: [],
                    isHalfDay: false,
                },
                events: [],
                leaves: [],
                leaveHeaders: [
                    { text: "EmployeeName", value: "employeeName", sortable: false },
                    { text: "Start", value: "start", sortable: false },
                    { text: "End", value: "end", sortable: false },
                    { text: "Days", value: "days", sortable: false },
                    { text: "CreatedAt", value: "createdAt", sortable: false, },
                    { text: "Status", value: "leaveStatus", sortable: false },
                    { text: "Actions", value: "actions", sortable: false }
                ],
                leaveStatistics: [],
                leaveStatisticHeaders: [
                    { text: "EmployeeName", value: "employeeName", sortable: false },
                    { text: "Entitlement (days)", value: "entitlement", sortable: false },
                    { text: "Used (days)", value: "usedDays", sortable: false },
                    { text: "Remain (days)", value: "remainDays", sortable: false }
                ],
            };
        },
        methods: {
            saveLeave() {
                var body;
                if (this.leave.dates.length == 1) {

                    body = {
                        employeeId: this.leave.employeeId,
                        start: this.leave.dates[0],
                        end: this.leave.dates[0],
                        isHalfDay: this.leave.isHalfDay
                    };
                } else {

                    if (this.leave.dates[0] < this.leave.dates[1]) {

                        body = {
                            employeeId: this.leave.employeeId,
                            start: this.leave.dates[0],
                            end: this.leave.dates[1],
                            isHalfDay: this.leave.isHalfDay
                        };
                    } else {

                        body = {
                            employeeId: this.leave.employeeId,
                            start: this.leave.dates[1],
                            end: this.leave.dates[0],
                            isHalfDay: this.leave.isHalfDay
                        };
                    }
                }

                LeaveService.create(body)
                    .then(() => {

                        this.refreshList();
                    })
                    .catch((e) => {
                        alert(e.response.data);
                    });
            },
            getLeaves() {
                const year = new Date().getFullYear();
                LeaveService.getByYear(year)
                    .then((response) => {
                        this.events = [];
                        this.leaves = response.data.map(this.getDisplayLeave);

                        for (let i = 0; i < this.leaves.length; i++) {
                            let leave = this.leaves[i];

                            if (leave.leaveStatus == 'PENDING' || leave.leaveStatus == 'APPROVED') {

                                this.events.push({
                                    name: (leave.isHalfDay ? 'Half ': 'All day ') + leave.employeeName ,
                                    start: leave.start,
                                    end: leave.end,
                                })
                            }
                        }
                    })
                    .catch((e) => {
                        alert(e.response.data);
                    });

            },

            refreshList() {

                this.getLeaves();
                this.getLeaveStatistics();
            },

            approveLeave(id) {
                LeaveService.update(id, "APPROVED")
                    .then(() => {

                        this.refreshList();
                    })
                    .catch((e) => {
                        alert(e.response.data);
                    });
            },

            cancelLeave(id) {
                LeaveService.update(id, "CANCELED")
                    .then(() => {

                        this.refreshList();
                    })
                    .catch((e) => {
                        alert(e.response.data);
                    });
            },

            getDisplayLeave(leave) {
                return {
                    id: leave.leaveId,
                    employeeName: leave.employeeName,
                    start: leave.start,
                    end: leave.end,
                    days: leave.days,
                    createdAt: leave.createdAt,
                    leaveStatus: leave.leaveStatus,
                    isHalfDay: leave.isHalfDay
                };
            },

            getLeaveStatistics() {
                LeaveService.getLeaveStatistics((new Date()).getFullYear())
                    .then((response) => {
                        this.leaveStatistics = response.data.map(this.getDisplayLeaveStatistics);
                    })
                    .catch((e) => {
                        console.log(e);
                    });
            },

            getDisplayLeaveStatistics(leaveStatistic) {
                return {
                    employeeName: leaveStatistic.employeeName,
                    entitlement: leaveStatistic.entitlement,
                    usedDays: leaveStatistic.usedDays,
                    remainDays: leaveStatistic.remainDays
                };
            }
        },
        mounted() {

            this.refreshList();
        }
    };
</script>

<style>
    .list {
        max-width: 1200px;
    }
</style>