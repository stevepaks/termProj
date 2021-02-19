<template>
    <v-row align="center" class="list px-auto mx-auto">
        <v-col cols="12" lg="12">
            <v-card-title>Leaves</v-card-title>
            <v-calendar :attributes='attrs' ></v-calendar>
        </v-col>
        <v-col cols="12" lg="12">
            <v-card class="mx-auto">
                <v-data-table :headers="headers" :items="leaves" disable-pagination :hide-default-footer="true">
                    <template v-slot:[`item.actions`]="{ item }">
                        <v-icon small class="mr-2" @click="approveLeave(item.id)">mdi-play</v-icon>
                        <v-icon small @click="cancelLeave(item.id)">mdi-stop</v-icon>
                    </template>
                </v-data-table>
            </v-card>
        </v-col>
    </v-row>
</template>
<script src='https://unpkg.com/v-calendar'></script>
<script>
    import LeaveService from "../services/LeaveService";
    export default {
        name: "leave-list",
        data() {
            return {
                leaves: [],
                headers: [
                    { text: "EmployeeName", value: "employeeName", sortable: false },
                    { text: "Start", value: "start", sortable: false, width: 103 },
                    { text: "End", value: "end", sortable: false, width: 103 },
                    { text: "Days", value: "days", sortable: false },
                    { text: "CreatedAt", value: "createdAt", sortable: false, width: 162 },
                    { text: "Status", value: "leaveStatus", sortable: false },
                    { text: "Actions", value: "actions", sortable: false }
                ],
                attrs: [
                    {
                        key: 'today',
                        highlight: 'red',
                        dates: new Date('2021-02-18 00:00:00'),
                    }
                ],
            };
        },
        methods: {
            getLeaves() {
                const year = new Date().getFullYear();
                LeaveService.getByYear(year)
                    .then((response) => {
                        this.leaves = response.data.map(this.getDisplayLeave);
                    })
                    .catch((e) => {
                        alert(e.response.data);
                    });
            },

            refreshList() {
                this.getLeaves();
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
                    leaveStatus: leave.leaveStatus
                };
            },
        },
        mounted() {
            this.getLeaves();
        }
    };
</script>

<style>
    .list {
        max-width: 750px;
    }
</style>