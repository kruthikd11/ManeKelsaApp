# Firebase needs to keep the no-argument constructor for the Worker model
-keepclassmembers class com.example.manekelsaapp.model.Worker {
    public <init>();
}