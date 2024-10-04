
--appointment_type
CREATE INDEX idx_appointment_type_allow_bookingON appointment_type (id) WHERE allow_booking = true;
CREATE INDEX idx_appointment_type_id ON schedule_template_details (appointment_type_id);

--schedule_template_details
CREATE INDEX idx_schedule_template_master_id ON schedule_template_details (schedule_template_master_id);
CREATE INDEX idx_schedule_template_day ON schedule_template_details (day);
CREATE INDEX idx_schedule_template_details_master_id_appointment_type_id ON schedule_template_details (schedule_template_master_id, appointment_type_id);

--schedule_template_master
CREATE INDEX idx_schedule_template_master_select_fields ON schedule_template_master (doctor_id, id, updated_at, clinic_id);
CREATE INDEX idx_schedule_template_master_doctor_id ON schedule_template_master (doctor_id);
CREATE INDEX idx_schedule_template_master_clinic_id ON schedule_template_master (clinic_id);