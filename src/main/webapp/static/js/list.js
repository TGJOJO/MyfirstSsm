function $$(id) {
    return document.getElementById(id);
}

window.onload=function() {
    let vue = new Vue({
        el: "#div_list",
        data: {
            pageInfo: "",
            eids: []
        },
        methods: {
            gotoPage: function (pageNum) {
                axios({
                    method: "GET",
                    url: "/project1/emp",
                    params: {
                        pageNo: pageNum
                    }
                }).then(function (value) {
                    let Msg = value.data;
                    let pageInfo = Msg.extend.pageInfo;
                    vue.pageInfo=pageInfo;
                }).catch(function (reason) {
                })
            },
            openAddModal: function () {
                //清空表单，防止第二次逃验证
                vue1.empName = "";
                vue1.email = "";

                $('#addEmpModal').modal({
                    backdrop:"static"
                });
            },
            openEditModal: function (emp) {
                vue2.getEmp(emp);
                $('#updateEmpModal').modal({
                    backdrop:"static"
                });
            },
            delEmp: function (id) {
                if(!window.confirm("是否确认删除？")) {
                    return false;
                }
                axios({
                    method: "DELETE",
                    url: "/project1/emp/" + id,
                    params: {

                    }
                }).then(function (value) {
                    let msg = value.data;
                    if(msg.code === 100) {
                        let pageInfo = vue.pageInfo;
                        let pageNum = pageInfo.pageNum;
                        if(pageInfo.pageNum !== 1 && pageInfo.pageNum === pageInfo.pages &&
                            pageInfo.total % pageInfo.pageSize === 1) {
                            pageNum = pageNum - 1;
                        }
                        vue.gotoPage(pageNum);
                        window.alert("删除成功,success");
                    }else {
                        window.alert("处理失败,failed");
                    }
                }).catch(function (reason) {

                })
            },
            delEmps: function () {
                if(vue.eids.length !== 0) {
                    if(window.confirm("你确定要删除这" + vue.eids.length + "条记录吗")) {
                        let idStr = "";
                        for(let i = 0; i < vue.eids.length; i++) {
                            idStr = idStr + vue.eids[i];
                            if (i !== vue.eids.length - 1) {
                                idStr = idStr + "-";
                            }
                        }

                        vue.delEmp(idStr);

                        let checkItems = $(".check_item:checked");
                        for(let i = 0; i < checkItems.length; i++) {
                            checkItems[i].checked = false;
                        }
                    }
                }
            },
            checkAll: function () {
                //全选自动将下方的选项框也选上
                $("#check_all").prop("checked");
                $(".check_item").prop("checked", $("#check_all").prop("checked"));

                if($("#check_all")[0].checked) {
                    let empList = vue.pageInfo.list;
                    for(let i = 0; i < empList.length; i++) {
                        vue.eids[i] = empList[i].eid;
                    }
                }else {
                    vue.eids[i] = [];
                }
            },
            checkItem: function (eid) {
                //下面的选项框全部选上了，全选框也自动选上
                let flag = $(".check_item:checked").length === $(".check_item").length;
                $("#check_all").prop("checked", flag);

                if(vue.eids.includes(eid)) {
                    let index = vue.eids.indexOf(eid);
                    vue.eids.splice(index, 1);
                }else {
                    vue.eids.push(eid);
                }
            }
        },
        mounted: function () {
            this.gotoPage(1);
        }
    });

    let vue1 = new Vue({
        el: "#addEmpModal",
        data: {
            empName: "",
            age: null,
            sex: "男",
            email: "",
            did: 1,
            deptList: {}
        },
        methods: {
            addEmp: function () {
                if(!this.checkEmpName() || !this.checkEmail()) {
                    return false;
                }

                if($$("but_addEmp").getAttribute("axios-va") === "error") {
                    return false;
                }

                axios({
                    method: "POST",
                    url: "/project1/emp",
                    params: {
                        empName: vue1.empName,
                        age: vue1.age,
                        sex: vue1.sex,
                        email: vue1.email,
                        did: vue1.did
                    }
                }).then(function (value) {
                    let msg = value.data;
                    if(msg.code === 200) {
                        //处理失败,说明校验不通过
                        let errors = msg.extend.errorFields;
                        //{codes: Array(4), arguments: Array(3), defaultMessage: '姓名必须为英文输入3-30位，中文输入2-20位', objectName: 'employee', field: 'empName', …}
                        for(let error of errors) {
                            if(error.field === "empName") {
                                const nameSpan = $$("nameSpan");
                                const divName = $$("div_name");
                                let faultMsg = $$("p_faultMsg");
                                faultMsg.innerText = error.defaultMessage;
                                divName.classList.add("has-error");
                                nameSpan.style.visibility="visible";
                            }
                            if(error.field === "email") {
                                const emailSpan = $$("emailSpan");
                                const divEmail = $$("div_email");
                                divEmail.classList.add("has-error");
                                emailSpan.style.visibility="visible";
                            }
                        }
                    }else {
                        $('#addEmpModal').modal('hide');
                        let pageInfo = vue.pageInfo;
                        let page = pageInfo.pages;
                        if(pageInfo.total % pageInfo.pageSize === 0) {
                            page = page + 1;
                        }
                        vue.gotoPage(page);
                    }
                }).catch(function (reason) {
                })
            },
            getDeptList: function () {
                axios({
                    method: "GET",
                    url: "/project1/dept",
                    params: {

                    }
                }).then(function (value) {
                    let Msg = value.data;
                    vue1.deptList = Msg.extend.deptList;
                    vue2.deptList = Msg.extend.deptList;
                }).catch(function (reason) {
                })
            },
            checkEmpNameIsRepeat: function () {
                axios({
                    method: "GET",
                    url: "/project1/checkEmpName",
                    params: {
                        empName: this.empName
                    }
                }).then(function (value) {
                    let Msg = value.data;
                    const nameSpan = $$("nameSpan");
                    const divName = $$("div_name");
                    //Msg.code=200说明处理失败，有重名的
                    if(Msg.code === 200) {
                        //这里不能通过返回true或false，然后通过if语句来判断是否有重名，
                        // 因为在得到返回值之前，if语句就已经做了判断，等于说if语句是失效的
                        let faultMsg = $$("p_faultMsg");
                        faultMsg.innerText="SSS用户名重复";
                        divName.classList.add("has-error");
                        nameSpan.style.visibility="visible";
                        $$("but_addEmp").setAttribute("axios-va","error");
                    } else {
                        divName.classList.remove("has-error");
                        nameSpan.style.visibility="hidden";
                        $$("but_addEmp").setAttribute("axios-va","success");
                    }
                }).catch(function (reason) {

                })
            },
            //这里加async表示这个是个同步方法，这里必须是同步方法，如果是异步，还没等axios返回结果
            //if语句就已经进行判断，或者已近进行赋值，所以必须让checkEmpNameIsRepeat方法单独执行
            checkEmpName: function () {
                //校验姓名格式
                const inputName = $$("inputName");
                const name = inputName.value;
                const nameReg = /(^[A-Za-z0-9]{3,30}$)|(^[\u4e00-\u9fa5]{2,20}$)/;
                const nameSpan = $$("nameSpan");
                const divName = $$("div_name");

                if(!nameReg.test(name)) {
                    let faultMsg = $$("p_faultMsg");
                    faultMsg.innerText="FFF英文输入3-30位，中文输入2-20位";
                    divName.classList.add("has-error");
                    nameSpan.style.visibility="visible";
                    return false;
                }

                divName.classList.remove("has-error");
                nameSpan.style.visibility="hidden";

                this.checkEmpNameIsRepeat();
                if($$("but_addEmp").getAttribute("axios-va") === "error") {
                    return false;
                }

                return true;
            },
            checkEmail: function() {
                //请输入正确的邮箱格式
                const exampleInputEmail1 = $$("exampleInputEmail1");
                const email = exampleInputEmail1.value;
                const emailReg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
                const emailSpan = $$("emailSpan");
                const divEmail = $$("div_email");
                if(!emailReg.test(email)) {
                    divEmail.classList.add("has-error");
                    emailSpan.style.visibility="visible";
                    return false;
                }else {
                    divEmail.classList.remove("has-error");
                    emailSpan.style.visibility="hidden";
                }

                return true;
            }
        },
        mounted: function () {
            this.getDeptList();
        }
    });

    let vue2 = new Vue({
        el: "#updateEmpModal",
        data: {
            eid: 0,
            empName: "",
            age: 0,
            sex: "",
            email: "",
            did: 0,
            deptList: {}
        },
        methods: {
            getEmp: function (emp) {
                this.eid = emp.eid;
                this.empName = emp.empName;
                this.age = emp.age;
                this.sex = emp.sex;
                this.email = emp.email;
                this.did = emp.did;
            },
            updateEmp: function () {
                if(!this.checkEmail()) {
                    return false;
                }
                axios({
                    method: "PUT",
                    url: "/project1/emp",
                    params: {
                        eid: this.eid,
                        empName: this.empName,
                        age: this.age,
                        sex: this.sex,
                        email: this.email,
                        did: this.did,
                    }
                }).then(function (value) {
                    let msg = value.data;
                    if(msg.code === 100) {
                        window.alert("修改成功！");
                        $('#updateEmpModal').modal('hide');
                        vue.gotoPage(vue.pageInfo.pageNum);
                    }else {
                        //处理失败,说明校验不通过
                        let errors = msg.extend.errorFields;
                        //{codes: Array(4), arguments: Array(3), defaultMessage: '姓名必须为英文输入3-30位，中文输入2-20位', objectName: 'employee', field: 'empName', …}
                        for(let error of errors) {
                            const emailSpan = $$("emailSpan_update");
                            const divEmail = $$("div_email_update");
                            divEmail.classList.add("has-error");
                            emailSpan.style.visibility="visible";
                        }
                    }
                }).catch(function (reason) {

                })
            },
            checkEmail: function() {
                //请输入正确的邮箱格式
                const exampleInputEmail = $$("input_emp_edit_email");
                const email = exampleInputEmail.value;
                const emailReg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
                const emailSpan = $$("emailSpan_update");
                const divEmail = $$("div_email_update");
                if(!emailReg.test(email)) {
                    divEmail.classList.add("has-error");
                    emailSpan.style.visibility="visible";
                    return false;
                }else {
                    divEmail.classList.remove("has-error");
                    emailSpan.style.visibility="hidden";
                }

                return true;
            },
        }
    });
}

//如果想要发送一部请求，我们需要一个关键的对象 XMLHttpRequest
// let xmlHttpRequest;
//
// function createXMLHttpRequest() {
//     if(window.XMLHttpRequest) { //符合DOM2标准的浏览器，我们需要一个关键的对象：XMLHttpRequest
//         xmlHttpRequest = new XMLHttpRequest;
//     }else if(window.ActiveXObject){
//         try {
//             xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
//         }catch (e) {
//             xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
//         }
//     }
//
// }
//
// function checkEmpName(empName) {
//     createXMLHttpRequest();
//     const url = "/project1/emp/" + empName;
//     xmlHttpRequest.open("GET", url, true);
//     //设置回调函数
//     xmlHttpRequest.onreadystatechange = checkEmpNameCB;
//     //发送请求
//     xmlHttpRequest.send();
// }
//
// function checkEmpNameCB() {
//     if(xmlHttpRequest.readyState===4 && xmlHttpRequest.status===200) {
//         //表示服务器端响应给我的文本内容
//         //alert(xmlHttpRequest.responseText);
//         let response = xmlHttpRequest.response;
//         if(response.code === 200) {
//             alert("已有重名用户");
//         }
//     }
// }

