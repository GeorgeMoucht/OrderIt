from django.core.management.base import BaseCommand
from django.contrib.auth import get_user_model

User = get_user_model()

class Command(BaseCommand):
    help = 'Seed the database with initial user data'

    def handle(self, *args, **kwargs):
        if not User.objects.filter(username='admin').exists():
            User.objects.create_superuser(
                username='admin',
                email='admin@example.com',
                password='admin',
                first_name='Admin',
                last_name='User'
            )
            self.stdout.write(self.style.SUCCESS("Superuser 'admin' created"))

            users = [
                {
                    'username': 'john',
                    'email': 'john@example.com',
                    'password': 'password123',
                    'first_name': 'John',
                    'last_name': 'Doe'
                },
                {
                    'username': 'sara',
                    'email': 'sara@example.com',
                    'password': 'password123',
                    'first_name': 'Sara',
                    'last_name': 'Smith'
                },
                {
                    'username': 'mike',
                    'email': 'mike@example.com',
                    'password': 'password123',
                    'first_name': 'Mike',
                    'last_name': 'Brown'
                },
                {
                    'username': 'anna',
                    'email': 'anna@example.com',
                    'password': 'password123',
                    'first_name': 'Anna',
                    'last_name': 'Taylor'
                },
            ]


        for user in users:
            if not User.objects.filter(username=user['username']).exists():
                User.objects.create_user(
                    username=user['username'],
                    email=user['email'],
                    password=user['password'],
                    first_name=user['first_name'],
                    last_name=user['last_name'],
                )

                self.stdout.write(self.style.SUCCESS(f"User '{user['username']}' created"))

        self.stdout.write(self.style.SUCCESS("User seeding complete!"))
