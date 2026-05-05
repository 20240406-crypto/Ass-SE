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
    

class User(models.Model):
    Name = models.CharField(max_length=40)
    Password = models.CharField(max_length=20)
    FakeBackUP = models.CharField(max_length=40)

    def __str__(self):
        return self.Name