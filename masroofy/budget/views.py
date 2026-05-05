from django.shortcuts import render, redirect
from .models import BudgetCycle


def setBudget(request):
    if request.method == "POST":
        amount = request.POST.get("amount")
        start_date = request.POST.get("start_date")
        end_date = request.POST.get("end_date")

        error = None

        if not amount or not start_date or not end_date:
            error = "All fields are required"

        elif float(amount) <= 0:
            error = "Amount must be greater than 0"

        elif start_date > end_date:
            error = "Start date must be before end date"

        if error:
            return render(request, "budget/set_budget.html", {
                "error": error
            })

        BudgetCycle.objects.create(
            amount=amount,
            start_date=start_date,
            end_date=end_date
        )

        return redirect("dashboard")

    return render(request, "budget/set_budget.html")


def dashboard(request):
    budget = BudgetCycle.objects.last()

    if not budget:
        return render(request, "budget/dashboard.html", {
            "error": "No budget found"
        })

    return render(request, "budget/dashboard.html", {
        "budget": budget,
        "daily_limit": budget.daily_limit_calc()
    })