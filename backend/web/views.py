from django.shortcuts import render, redirect
import json
from django.http import JsonResponse
from django.contrib.auth import authenticate, login
from django.contrib.auth.decorators import login_required
from django.contrib.auth import get_user_model
from django.contrib.auth import logout
from .decorators import superuser_required
from django.views.decorators.csrf import csrf_exempt
from django.shortcuts import get_object_or_404
from .serializers import UserUpdateSerializer
from rest_framework.parsers import JSONParser
from django.views.decorators.http import require_POST
from django.core.paginator import Paginator
from django.db.models import Q
from django.utils.http import urlencode
from api.models import Table, MenuItem, MenuCategory


User = get_user_model()

def custom_permission_denied_view(request, exception=None):
    return render(request, 'web/403.html', status=403)

def login_view(request):
    if request.method == 'POST':
        username = request.POST.get("username")
        password = request.POST.get("password")
        user = authenticate(request, username=username, password=password)

        if user is not None:
            if user.is_superuser:
                login(request, user)
                return redirect('dashboard')
            else:
                return render(request, 'web/login.html', {
                    'error': 'You do not have permission to access the admin dashboard.'
                })
        else:
            return render(request, 'web/login.html', {
                'error': 'Invalid credentials'
            })
    return render(request, 'web/login.html')


@login_required
@superuser_required
def manage_users(request):
    user_list = User.objects.order_by("id")
    paginator = Paginator(user_list, 10)

    page_number = request.GET.get("page")
    page_obj = paginator.get_page(page_number)

    return render(request, "web/manage_users.html", {
        # "page_obj": page_obj,
        # "users": page_obj.object_list,
        # "active_page": "users"
        "users": page_obj,
        "active_page": "users"
    })

@login_required
@superuser_required
def manage_tables(request):
    tables = Table.objects.order_by("id")
    return render(request, "web/manage_tables.html", {
        "tables": tables,
        "active_page": "tables"
    })

@login_required
@superuser_required
def dashboard(request):
    return render(request, "web/dashboard.html", {
        "active_page": "dashboard"
    })


@csrf_exempt
@require_POST
@login_required
@superuser_required
def update_user_view(request):
    try:
        data = json.loads(request.body)
        user_id = data.get("id")
        user = get_object_or_404(User, id=user_id)

        if user.is_superuser:
            return JsonResponse({
                "success": False,
                "message": "You cannot modify a superuser."
            }, status=403)

        serializer = UserUpdateSerializer(user, data=data)

        if serializer.is_valid():
            serializer.save()
            print("💾 Saved user with new role:", user.role)
            return JsonResponse({
                'success': True,
                'message': 'User updated successfully'
            })
        else:
            return JsonResponse({
                'success': False,
                'message': serializer.errors
            }, status=400)

    except json.JSONDecodeError:
        return JsonResponse({
            'success': False,
            'message': 'Invalid JSON payload.'
        }, status=400)

    except Exception as e:
        return JsonResponse({
            'success': False,
            'message': str(e)
        }, status=400)
    

@csrf_exempt
@require_POST
@login_required
@superuser_required
def delete_user_view(request):
    try:
        data = json.loads(request.body)
        print("Incoming data:", data)

        user_id = data.get("id")

        if not user_id:
            return JsonResponse({
                "success": False,
                "message": "Missing user ID."
            }, status=400)
        
        user = get_object_or_404(User, id=user_id)

        if user.is_superuser:
            return JsonResponse({
                "success": False,
                "message": "Cannot delete a superuser."
            }, status=403)

        user.delete()

        return JsonResponse({
            "success": True,
            "message": "User deleted successfully."
        })

    except Exception as e:
        return JsonResponse({
            "success": False,
            "message": str(e)
        }, status=400)
    
@csrf_exempt
@require_POST
@login_required
@superuser_required
def delete_table_view(request):
    try:
        data = json.loads(request.body)
        table_id = data.get("id")

        if not table_id:
            return JsonResponse({
                "success": False,
                "message": "Missing table ID."
            }, status=400)
        
        from api.models import Table  # το σωστό import
        table = get_object_or_404(Table, id=table_id)
        table.delete()

        return JsonResponse({
            "success": True,
            "message": "Table deleted successfully."
        })

    except Exception as e:
        return JsonResponse({
            "success": False,
            "message": str(e)
        }, status=400)

@csrf_exempt
@require_POST
@login_required
@superuser_required
def create_table_view(request):
    try:
        data = json.loads(request.body)
        name = data.get("name")

        if not name:
            return JsonResponse({
                "success": False,
                "message": "Name is required."
            }, status=400)

        Table.objects.create(name=name)

        return JsonResponse({
            "success": True,
            "message": "Table created successfully."
        })

    except Exception as e:
        return JsonResponse({
            "success": False,
            "message": str(e)
        }, status=500)

@csrf_exempt
@require_POST
@login_required
@superuser_required
def create_user_view(request):
    try:
        data = json.loads(request.body)
        user = User.objects.create_user(
            username=data["username"],
            first_name=data.get("first_name", ""),
            last_name=data.get("last_name", ""),
            email=data.get("email", ""),
            password=data["password"],
            role=data.get("role", "waiter")
        )
        return JsonResponse({"success": True})
    except Exception as e:
        return JsonResponse({"success": False, "message": str(e)}, status=400)


@login_required
@superuser_required
def logout_view(request):
    logout(request)
    return redirect('login')


@login_required
@superuser_required
def manage_menu_categories(request):
    search = request.GET.get("search", "").strip()

    # Filter categories
    category_list = MenuCategory.objects.select_related('parent').order_by("id")
    if search:
        category_list = category_list.filter(
            Q(name__icontains=search) | Q(id__iexact=search)
        )

    paginator = Paginator(category_list, 15)
    page_number = request.GET.get("page")
    page_obj = paginator.get_page(page_number)

    return render(request, "web/manage_menu_categories.html", {
        "categories": page_obj,
        "active_page": "menu_categories",
        "search": search
    })

@csrf_exempt
@require_POST
@login_required
@superuser_required
def create_category_view(request):
    try:
        data = json.loads(request.body)
        name = data.get("name")
        parent_id = data.get("parent")

        if not name:
            return JsonResponse({
                "success": False,
                "message": "Category name is required."
            }, status=400)

        parent = None
        if parent_id:
            parent = get_object_or_404(MenuCategory, id=parent_id)

        MenuCategory.objects.create(name=name, parent=parent)

        return JsonResponse({
            "success": True,
            "message": "Category created successfully."
        })

    except Exception as e:
        return JsonResponse({
            "success": False,
            "message": str(e)
        }, status=500)


@csrf_exempt
@require_POST
@login_required
@superuser_required
def delete_category_view(request):
    try:
        data = json.loads(request.body)
        category_id = data.get("id")

        if not category_id:
            return JsonResponse({
                "success": False,
                "message": "Missing category ID."
            }, status=400)

        category = get_object_or_404(MenuCategory, id=category_id)
        category.delete()

        return JsonResponse({
            "success": True,
            "message": "Category deleted successfully."
        })

    except Exception as e:
        return JsonResponse({
            "success": False,
            "message": str(e)
        }, status=400)


@csrf_exempt
@require_POST
@login_required
@superuser_required
def update_category(request):
    if request.method == "POST":
        try:
            data = json.loads(request.body)
            cat = MenuCategory.objects.get(id=data["id"])
            cat.name = data["name"]
            cat.parent_id = data["parent"] if data["parent"] else None
            cat.save()
            return JsonResponse({"success": True})
        except MenuCategory.DoesNotExist:
            return JsonResponse({"success": False, "message": "Category not found"})
        except Exception as e:
            return JsonResponse({"success": False, "message": str(e)})
