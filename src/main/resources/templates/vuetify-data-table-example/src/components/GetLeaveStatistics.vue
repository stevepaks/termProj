<template>
    <v-row align="center" class="list px-auto mx-auto">
        <v-col cols="12" lg="12">
            <v-card class="mx-auto">
                <v-card-title>Leave Statistics</v-card-title>
                <v-data-table :headers="headers" :items="leaveStatistics" disable-pagination :hide-default-footer="true">
                </v-data-table>
            </v-card>
        </v-col>
    </v-row>
</template>

<script>
    import LeaveService from "../services/LeaveService";
    export default {
        name: "leave-list",
        data() {
            return {
                leaveStatistics: [],
                headers: [
                    { text: "EmployeeName", value: "employeeName", sortable: false },
                    { text: "Entitlement", value: "entitlement", sortable: false },
                    { text: "UsedDays", value: "usedDays", sortable: false },
                    { text: "RemainDays", value: "remainDays", sortable: false }
                ],
            };
        },
        methods: {
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
            this.getLeaveStatistics();
        },
    };
</script>

<style>
    .list {
        max-width: 750px;
    }
</style>