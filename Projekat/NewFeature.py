import os
import argparse


def update_selected_config_line(message: str, new_line_value: str, line_number: int = 0) -> None:
    if(os.path.isdir(args.config_list[0])):
        with open(".newFeatureConfig", "w") as config:
            config.writelines(
                [args.config_list[0], "\n" + args.config_list[1]])
        print(message)
    else:
        print("Invalid config parametars! Check the parametars and run script again!")


def read_line_from_config_file(line_number: int) -> str:
    with open(".newFeatureConfig", "r") as config:
        lines = config.readlines()

    if(len(lines) <= line_number):
        return ""
    return lines[line_number]


# def get_class_name() -> str:
#     return args.name[0].upper() + args.name[1:].lower()


# def create_repository_impl_file(path: str, package: str) -> None:
#     class_name = get_class_name()
#     with open(path + class_name + "RepositoryImpl.kt", "w") as repository:
#         repository.write("package " + package + ".data.repository\n\nimport " + package + ".domain.repository." +
#                          class_name + "Repository\n\nclass " + class_name + "RepositoryImpl(): " + class_name + "Repository {}")


# def create_repository_interface_file(path: str, package: str) -> None:
#     class_name = get_class_name()
#     with open(path + class_name + "Repository.kt", "w") as repository:
#         repository.write("package " + package +
#                          ".domain.repository\n\ninterface " + class_name + "Repository {}")


# def create_repository_model_file(path: str, package: str) -> None:
#     class_name = get_class_name()
#     with open(path + class_name + ".kt", "w") as model:
#         model.write("package " + package + ".domain.model\n\ndata class " + class_name +
#                     "(val id: Int) {}\n\nclass Invalid" + class_name + "Exception(message: String): Exception(message)")


# def create_crud_use_cases(path: str, package: str) -> None:
#     class_name = get_class_name()
#     create_use_case(path, package, "Get" + class_name + "s", class_name)
#     create_use_case(path, package, "Get" + class_name, class_name)


# def create_use_case(path: str, package: str, name: str, class_name: str) -> None:
#     with open(path + name + ".kt", "w") as use_case:
#         use_case.write("package " + package +
#                        ".domain.use_case\n\nimport " + package + ".domain.model.Invalid" + class_name + "Exception\n")


parser = argparse.ArgumentParser()

parser.add_argument("-c", "--config-list", nargs=2,
                    help="Main project folder path and main project package.")
parser.add_argument("-p", "--package", help="Main project package.")
parser.add_argument("-n", "--name", help="New feature's name.")
parser.add_argument("-s", "--screen-list",
                    help="List of screens in new feature.", nargs="+", default=[])


args = parser.parse_args()

if(os.path.isfile(".newFeatureConfig")):
    if(args.config_list):
        update_selected_config_line(
            "Config file updated! Other params are ignored, so run the script again!", args.config_list)
    else:
        path = read_line_from_config_file(0)[:-1]
        package = read_line_from_config_file(1)

        if(args.name):
            name = "feature_" + args.name.lower()
            if(os.path.isdir(path + name)):
                print("Feature with provided name already exists!")

            else:
                path = path + name
                package = package + "." + name
                os.mkdir(path)

                os.mkdir(path + "/data")
                os.mkdir(path + "/data/data_source")
                os.mkdir(path + "/data/repository")
                # create_repository_impl_file(
                #     path + "/data/repository/", package)

                os.mkdir(path + "/domain")
                os.mkdir(path + "/domain/model")
                # create_repository_model_file(path + "/domain/model/", package)
                os.mkdir(path + "/domain/repository")
                # create_repository_interface_file(
                    # path + "/domain/repository/", package)
                os.mkdir(path + "/domain/use_case")
                os.mkdir(path + "/domain/util")

                os.mkdir(path + "/presentation")
                os.mkdir(path + "/presentation/util")

                if(args.screen_list):
                    for screen in args.screen_list:
                        os.mkdir(path + "/presentation/" + screen)
                        os.mkdir(path + "/presentation/" +
                                 screen + "/components")

                print(name + " succesfully added!")

        else:
            print("Feature name not provided! Check the parametars and run script again!")


elif(args.config_list):
    update_selected_config_line(
        "Config file created!", args.config_list)

else:
    print("Run script again and pass -c parametar containing configuration parametars (main project folder path and project package name)!")
