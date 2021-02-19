<template>
    <div class="submit-form mt-3 mx-auto">
        <p class="headline">Add Leave</p>

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
</template>

<script>
    import LeaveService from "../services/LeaveService";

    export default {
        name: "add-leave",
        data() {
            return {
                leave: {
                    employeeId: "",
                    dates: [],
                    isHalfDay: false,
                }
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

                    body = {
                        employeeId: this.leave.employeeId,
                        start: this.leave.dates[0],
                        end: this.leave.dates[1],
                        isHalfDay: this.leave.isHalfDay
                    };
                }

                LeaveService.create(body)
                    .then(() => {
                        window.location.href="http://localhost:8080";
                    })
                    .catch((e) => {
                        alert(e.response.data);
                    });
            }
        }
    };
</script>

<style>
    .submit-form {
        max-width: 300px;
    }
</style>