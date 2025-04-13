# OrderIt Backend

This is the backend API and web dashboard for **OrderIt**, a PDA system for restaurants, bars, and more. It uses Django, Django REST Framework, and JWT authentication.

---

# Getting Started

### 1. Clone the project

```bash
git clone https://github.com/your-user/OrderIt.git
```

### 2. Set up a virtual environment
In the root directory out of backend and frontend write this command in the terminal to create the venv enviroment.

```bash
cd OrderIt
python -m venv venv
source venv/bin/activate #on Windows venv\Scripts\activate
```

### 3. Install dependencies
Write these commands in terminal to download dependencies

```bash
cd backend
pip install -r requirements.txt
```

### 4. Run migrations
```bash
python manage.py migrate
```

### 5. Seed demo users
Run this to seed the table of users with dummy data.
```bash
python manage.py seed_users
```

This will create:
- 1 Superuser (username: `admin`, password: `admin`)
- 3 regular demo users with fake names and emails

### 6. Run development server
To run the backend do this in terminal
```bash
python manage.py runserver 0.0.0.0:8000
```


## JWT Auth

The API uses ***JWT*** authentication via `djangorestframework-simplejwt`.

- Login: `POST /api/authentication/login/`
- Refresh: `POST /api/authentication/refresh/`
- Logout: `POST /api/authentication/logout/`

--- 

## Structure

- `api/` - Django app for backend APIs
- `web/` - Django app for admin web dashboard
- `accounts/` - Custom user model & seeder
- `static/` - CSS & assets

## Tech Stack

- Django
- Django REST Framework
- SimpleJWT
- SQLite
- Android Studio


## Test login credentials from seeder

| Username | Password | Role      |
|----------|----------|-----------|
| admin    | admin    | Superuser |
| demo1    | demo     | User      |
| demo2    | demo     | User      |
| demo3    | demo     | User      |

---

## Notes

- Change token lifetimes in `settings.py` under `SIMPLE_JWT`
- Customize dashboard styles in `static/css/style.css`