from django.db import models
from datetime import datetime

class BudgetCycle(models.Model):
    amount = models.FloatField()
    start_date = models.DateField()
    end_date = models.DateField()
    is_active = models.BooleanField(default=True)

    def daily_limit_calc(self):
        days = (self.end_date - self.start_date).days + 1
        return self.amount / days