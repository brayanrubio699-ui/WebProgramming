import pywhatkit

print("\n") 
print("Type a number to message:") 
number = input()

print("\n") 
print("Type a message:") 
message = input()

pywhatkit.sendwhatmsg_instantly(number, message, 10)

pywhatkit.sendwhatmsg("+910123456789", "Hi", 13, 30, 15, True, 2)

pywhatkit.sendwhats_image("AB123CDEFGHijklmn", "Images/Hello.png", "Hello")

pywhatkit.sendwhats_image("+910123456789", "Images/Hello.png")

pywhatkit.sendwhatmsg_to_group("AB123CDEFGHijklmn", "Hey All!", 0, 0)

pywhatkit.sendwhatmsg_to_group_instantly("AB123CDEFGHijklmn", "Hey All!")

pywhatkit.playonyt("PyWhatKit")