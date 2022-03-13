en_phrases = {
    "create-a-function-prototype":"Create a function prototype",
    "text-title" : "Exerciser for writing a function prototype in the C language",
    "text-1st_title" : "Select all data elements from the task text",
    "text-1st_description" : "You must select each data element from the text and click on the button.",
    "text-1st_button" : "Check data element",
    "text-data_element" : "Data element",
    "text-data_presentation" : "Data presentation",
    "text-data_direction" : "Data direction",
    "text-data_component" : "Data component",

    "text-input_data" : "Input data",
    "text-output_data" : "Output data",
    "text-updatable_data" : "Changed data",

    "text-return_value" : "Return value",
    "text-input_parameter" : "Input parameter",
    "text-output_parameter" : "Output parameter",
    "text-updatable_parameter" : "Changed parameter",

    "text-function_prototype" : "Function prototype",
    "text-create_function_prototype" : "Create function prototype from lexems",

    "text-logical_type" : "Logical type",
    "text-transfer" : "Method of passing data",

    "entity": "An object with 3 properties: day (number), month (number), year (number)",
    "freefields" : "3 scalars: day (number), month (number), year (number)",
    "freefields4" : "4 scalars: day (number), month (number), year (number), count of days from 1900 (number)",
    "freefields2" : "2 scalars: day (number), month (number)",
    "number" : "A number",
    "collection3": "A collection of 3 elements of type \"number\"",
    "collectionN": "A collection of N elements of type \"number\"",
    "collectionNAndN": "A collection of N elements of type \"number\", size of collection (number)",

    "text-type_C-language" : "Datatype of C-language",
    "text-remove_last_lexem" : "Remove a last lexem"
}

ru_phrases = {
    "create-a-function-prototype":"Напишите прототип функции, которая...",
    "text-title" : "Тренажер для выработки навыков написания прототипа функции на языке Си",
    "text-1st_title" : "Выделите все элементы данных из текста задания",
    "text-1st_description" : "Вам необходимо поочередно выделить в тесте все элементы данных. После выделения каждого элемента данных необходимо нажать на кнопку",
    "text-1st_button" : "Отметить элемент данных",
    "text-data_element" : "Элемент данных",
    "text-data_presentation" : "Представление данных",
    "text-data_direction" : "Направление данных",
    "text-data_component" : "Компонент данных",

    "text-input_data" : "Входные данные",
    "text-output_data" : "Выходные данные",
    "text-updatable_data" : "Обновляемые данные",

    "text-return_value" : "Возвращаемое значение",
    "text-input_parameter" : "Входной параметр",
    "text-output_parameter" : "Выходной параметр",
    "text-updatable_parameter" : "Обновляемый параметр",

    "text-function_prototype" : "Прототип функции",
    "text-create_function_prototype" : "Составьте прототип функции из набора лексем",

    "text-logical_type" : "Логический тип",
    "text-transfer" : "Способ передачи",

    "entity": "Сущность из 3 характеристик: день (число), месяц (число), год (число)",
    "freefields" : "3 отдельных скаляра: день (число), месяц (число), год (число)",
    "freefields2" : "2 отдельных скаляра: день (число), месяц (число)",
    "number" : "Число",
    "collection3": "Коллекция из 3 элементов типа \"число\"",
    "collectionN": "Коллекция из N элементов типа \"число\"",
    "collectionNAndN": "Коллекция из N элементов типа \"число\", количество элементов N (число)",

    "text-type_C-language" : "Тип данных языка Си",
    "text-remove_last_lexem" : "Удалить последнюю лексему"
}

lang = "ru"
dic = (lang == "ru") ? ru_phrases : en_phrases
studentID = 0
function changeLang(l)
{
    lang = l
    dic = (lang == "ru") ? ru_phrases : en_phrases
}

function initPage()
{
    getLexemsOfProblem()
    for (let id in dic)
    {
        document.getElementById(id).innerText = dic[id]
    }
}

function getLexemsOfProblem()
{
    let div = document.getElementById("problem-lexems-for-selection")

    var xhr = new XMLHttpRequest()
    xhr.open('GET', '/answer/problemLexems?student='+studentID, false)
    xhr.send();


    if (xhr.status == 200) {
        let json = xhr.responseText
        alert(json)
        obj = JSON.parse(json)
        for (let key in obj)
        {
            let divLex = document.createElement("div")
            divLex.classList.add("lexem","col")
            let divLexP = document.createElement("p")
            divLexP.innerText = obj[key]
            divLex.appendChild(divLexP)
            div.appendChild(divLex)
        }
    } else {
        alert( "Internal error: " + xhr.statusText );
    }
}

function hideAll()
{
    var blocks=document.getElementsByClassName('serviceblock');
    document.getElementById('toDataDirectionsBtn').hidden = true;

    for (let item of blocks)
    {
        item.hidden=true
    }
}

function removeActiveClass()
{
    var nav_links = document.getElementsByClassName("nav-link");
    for (let item of nav_links)
    {
        item.classList.remove("active")
    }
}

function getSelectionRange()
{
    node1 = document.getSelection().anchorNode.parentNode.offsetParent
    node2 = document.getSelection().focusNode.parentNode.offsetParent
    array = node1.parentElement.getElementsByTagName("div")
    let startSelection = 0, endSelection = 0;
    for (i=0; i<array.length; i++)
    {
        if (array[i] == node1)
            startSelection = i+1;
        if (array[i] == node2)
            endSelection = i+1;
    }

    return {"start": startSelection, "end": endSelection};
}

function showMessage(message)
{
    element = document.getElementById("current_message");
    element.innerText = message;
    element.hidden = false;
    setTimeout(()=>{element.hidden = true;}, 2500);

}

function lockSelect(intaractionBlockID, selectIndex)
{
    element = document.getElementById(intaractionBlockID).getElementsByTagName("select")[selectIndex].disabled=true;
}

function addDataElementIntoTable(element_name, element_mission)
{
    let table = document.getElementsByTagName("table").item(0)
    let tbody = table.getElementsByTagName("tbody").item(0)
    let row = tbody.insertRow()
    row.setAttribute("dataelement", element_name)
    let cell = row.insertCell()
    cell.innerText = element_mission
    cell.style.width = "500px"

    row.style.borderColor = "#a3c4a3"


}

function addDataDirectionSelects()
{
    let table = document.getElementsByTagName("table").item(0)
    let tbody = table.getElementsByTagName("tbody").item(0)
    let rows = tbody.rows

    trs = tbody.getElementsByTagName("tr");
    let counter = 0
    for (let item of rows) {

        dataelement = trs.item(counter).getAttribute("dataelement")
        console.log(dataelement)

        var tr = trs.item(counter)

        let cell = item.insertCell();
        cell.style.width = "500px"
        let select = document.createElement("select")
        select.setAttribute("dataelement", dataelement)
        select.id = dataelement + "-select-direction"

        let optionSelect = document.createElement("option")
        optionSelect.text = "..."
        optionSelect.disabled = true
        optionSelect.selected = true

        let optionInput = document.createElement("option")
        optionInput.text = dic["text-input_data"]
        optionInput.value = "input"

        let optionOutput = document.createElement("option")
        optionOutput.text = dic["text-output_data"]
        optionOutput.value = "output"

        let optionUpdatable = document.createElement("option")
        optionUpdatable.text = dic["text-updatable_data"]
        optionUpdatable.value = "updatable"

        select.appendChild(optionSelect)
        select.appendChild(optionInput)
        select.appendChild(optionOutput)
        select.appendChild(optionUpdatable)

        select.style.margin = "20px"



        select.setAttribute("onchange",  "$.post('/answer/2',  'student="+studentID+"&elementName="+this.dataelement+"&elementDirection='+this.value,  function (data) {  obj = (data); let b = document.getElementById('"+this.dataelement+"-direction-alert'); b.innerText = obj.message.replace(/\\\\/g, ''); if (obj.correct=='false') {b.hidden = false; b.classList.remove('alert-success'); b.classList.add('alert-danger'); } else {setTrueDirection("+counter+"); document.getElementById('"+select.id+"').disabled=true; openDataDirectionsIfNeccesary(); b.hidden = true;   }  } )")

        cell.appendChild(select)



        let message_block = document.createElement("div")
        message_block.classList.add("alert")
        message_block.id = dataelement+"-direction-alert"
        message_block.style.width = "320px"
        message_block.style.margin = "20px"
        message_block.hidden = true



        message_block.setAttribute("role", "alert")
        message_block.innerText = "ОК"
        cell.appendChild(message_block)

        counter++
        //cell.innerText = "Входной/выходной/обновляемый"
    }



    let thead = table.getElementsByTagName("thead").item(0).getElementsByTagName("tr").item(0)
    thead.innerHTML += "<th scope='col'>"+dic["text-data_direction"]+"</th>"
}

function setTrueDirection(index)
{
    let table = document.getElementsByTagName("table").item(0)
    let tbody = table.getElementsByTagName("tbody").item(0)
    let rows = tbody.rows

    let trs = tbody.getElementsByTagName("tr");
    trs.item(index).setAttribute("correct_direction", "true")
}

function setTruePresentation(index)
{
    let table = document.getElementsByTagName("table").item(0)
    let tbody = table.getElementsByTagName("tbody").item(0)
    let rows = tbody.rows

    let trs = tbody.getElementsByTagName("tr");
    trs.item(index).setAttribute("correct_presentation", "true")
}

function allRowsHasRightDirections()
{
    let table = document.getElementsByTagName("table").item(0)
    let tbody = table.getElementsByTagName("tbody").item(0)
    let rows = tbody.rows

    let trs = tbody.getElementsByTagName("tr");
    for (let i=0; i<trs.length; i++)
    {
        if (trs.item(i).getAttribute("correct_direction") !== "true" )
            return false;
    }
    return true;
}

function allRowsHasRightPresentations()
{
    let table = document.getElementsByTagName("table").item(0)
    let tbody = table.getElementsByTagName("tbody").item(0)
    let rows = tbody.rows

    let trs = tbody.getElementsByTagName("tr");
    for (let i=0; i<trs.length; i++)
    {
        console.log("Item: "+i)
        if (trs.item(i).getAttribute("correct_presentation") !== "true" )
        {
            console.log("Hasn't presentation. Row: "+i)
            return false;
        }

    }
    return true;
}

function openDataDirectionsIfNeccesary()
{
    if (allRowsHasRightDirections())
    {
        addDataPresentations()
    }
}

function openDataComponentsIfNecessary()
{
    //let table = document.getElementsByTagName("table").item(0)
    //let tbody = table.getElementsByTagName("tbody").item(0)
    //let rows = tbody.rows

    //let trs = tbody.getElementsByTagName("tr");

    if (allRowsHasRightPresentations())
    {
        console.log("Start open components")
        addComponentsForAllDataElements();

    }
    else
    {
        console.log("Don't need open components")
    }
}

data_element_presentations = {}

function getDataElementPresentations(element)
{
    /*let xhr = new XMLHttpRequest()
    xhr.open('GET','/answer/presentations?student='+studentID+'&elementName='+element)
    xhr.send()

    //console.log("Response:")
    let presentationsList = xhr.response
    //console.log(presentationsList)
    return presentationsList;*/
    $.get('/answer/presentations?student='+studentID+'&elementName='+element, function (obj) { data_element_presentations[element] = obj  })
}

function addDataPresentations()
{
    let table = document.getElementsByTagName("table").item(0)
    let tbody = table.getElementsByTagName("tbody").item(0)
    let rows = tbody.rows

    let trs = tbody.getElementsByTagName("tr");
    let counter = 0
    for (let item of rows) {

        dataelement = trs.item(counter).getAttribute("dataelement")
        console.log("Dataelement: "+dataelement)




        let cell = item.insertCell();
        cell.style.width = "500px"
        let select = document.createElement("select")
        select.id = dataelement+"-select-presentation"
        select.setAttribute("dataelement", dataelement)

        let optionSelect = document.createElement("option")
        optionSelect.text = "..."
        optionSelect.disabled = true
        optionSelect.selected = true


        console.log("(Before select) Dataelement: "+ dataelement)
        select.appendChild(optionSelect)
        //getDataElementPresentations(dataelement)
        $.get('/answer/presentations?student='+studentID+'&elementName='+dataelement, function (obj) {
            data_element_presentations[dataelement] = obj

            console.log(data_element_presentations[dataelement])
            for (let presName in data_element_presentations[dataelement])
            {
                console.log("Presentation name: "+presName)
                let option = document.createElement("option")
                option.text = data_element_presentations[dataelement][presName]
                option.value = presName
                select.appendChild(option)
            }

        })

        /*
        let optionInput = document.createElement("option")
        optionInput.text = dic["entity"]
        optionInput.value = "entity"

        let optionOutput = document.createElement("option")
        optionOutput.text = dic["freefields"]
        optionOutput.value = "freefields"

        let optionUpdatable = document.createElement("option")
        optionUpdatable.text = dic["freefields2"]
        optionUpdatable.value = "freefields2"

        let optionUpdatable4 = document.createElement("option")
        optionUpdatable4.text = dic["freefields4"]
        optionUpdatable4.value = "freefields4"

        let optionNumber = document.createElement("option")
        optionNumber.text = dic["number"]
        optionNumber.value = "number"

        let optionCollection3Numbers = document.createElement("option")
        optionCollection3Numbers.text = dic["collection3"]
        optionCollection3Numbers.value = "collection3"

        let optionCollectionNNumbers = document.createElement("option")
        optionCollectionNNumbers.text = dic["collectionN"]
        optionCollectionNNumbers.value = "collectionN"

        let optionCollectionNNumbersAndN = document.createElement("option")
        optionCollectionNNumbersAndN.text = dic["collectionNAndN"]
        optionCollectionNNumbersAndN.value = "collectionNAndN"

        select.appendChild(optionSelect)
        select.appendChild(optionInput)
        select.appendChild(optionOutput)
        select.appendChild(optionUpdatable)
        select.appendChild(optionUpdatable4)
        select.appendChild(optionNumber)
        select.appendChild(optionCollection3Numbers)
        select.appendChild(optionCollectionNNumbers)
        select.appendChild(optionCollectionNNumbersAndN)*/


        select.style.margin = "20px"

        select.setAttribute("onchange",  "$.post('/answer/3',  'student="+studentID+"&elementName="+this.dataelement+"&elementPresentation='+this.value,  function (data) { obj = (data); b = document.getElementById('"+this.dataelement+"-presentation-alert'); b.innerText = obj.message; if (obj.correct=='false') {b.hidden = false; b.classList.remove('alert-success'); b.classList.add('alert-danger'); } else {setTruePresentation("+counter+"); getComponentsListForElement('"+dataelement+"'); getComponentsListForElement('"+dataelement+"');  b.hidden = true; document.getElementById('"+select.id+"').disabled=true  }   } ); openDataComponentsIfNecessary(); ")
        //select.setAttribute("onclick", "openDataComponentsIfNecessary();")

        cell.appendChild(select)

        let message_block = document.createElement("div")
        message_block.classList.add("alert")
        message_block.id = dataelement+"-presentation-alert"
        message_block.style.width = "320px"
        message_block.style.margin = "20px"
        message_block.hidden = true

        message_block.setAttribute("role", "alert")
        message_block.innerText = "ОК"
        cell.appendChild(message_block)

        counter++
            /*
        //cell.innerText = "Входной/выходной/обновляемый"
         */
    }



    let thead = table.getElementsByTagName("thead").item(0).getElementsByTagName("tr").item(0)
    thead.innerHTML += "<th scope='col'>"+dic["text-data_presentation"]+"</th>"
}

function setDataDirection(elementName, direction)
{
    element_direction[elementName] = direction;
}

var componentsOfElements = {};
var componentsOfElementsWithOrganization = {};
var returnValue = {};
var param_cells = [];
var type_cells = [];
var param_types = {};
var element_direction = {};

var types_map = {"Type_Int": "int", "Type_PointerToInt" : "int *", "Type_Float" : "float", "Type_Char": "char", "Type_StructDate" : "struct Date"}


function getAllComponents()
{
    var xhr = new XMLHttpRequest()
    xhr.open("GET", "/answer/components?student="+studentID)
    xhr.responseType = "json"
    xhr.send()
    xhr.onload = function () {
        componentsOfElements = xhr.response
    }
}

function getComponentsListForElement(elementName)
{
    xhr = new XMLHttpRequest();
    xhr.open('GET', '/answer/element_components?student='+studentID+'&elementName='+elementName);
    xhr.responseType = 'json';
    xhr.send();
// тело ответа {"сообщение": "Привет, мир!"}
    xhr.onload = function() {
        let responseObj = xhr.response;
        console.log(elementName)
        console.log(responseObj); // Привет, мир!
        componentsOfElements[elementName] = JSON.parse(JSON.stringify(responseObj))
    };
    return componentsOfElements[elementName]
}



function addComponentsForDataElement(elementName)
{
    let table = document.getElementsByTagName("table").item(0)
    let tbody = table.getElementsByTagName("tbody").item(0)
    let rows = tbody.rows




    let trs = tbody.getElementsByTagName("tr");
    let counter = 0
    for (let item of rows) {
        let currElement = trs.item(counter).getAttribute("dataelement")


        if (currElement == elementName)
        {
            let cell = trs.item(counter).insertCell()
            cell.style.width = "500px"
            let components = getComponentsListForElement(elementName)
            for (let key in components)
            {
                cell.innerText += components[key] + '\n'
            }
        }
        counter++
    }


}


function addComponentsForAllDataElements()
{
    getAllComponents()
    let table = document.getElementsByTagName("table").item(0)
    let tbody = table.getElementsByTagName("tbody").item(0)
    let rows = tbody.rows

    table.hidden = true;

    let table2  = document.createElement("table")
    table2.style.fontSize = "x-large"
    table2.classList.add("table","table-hover")
    table2.appendChild(document.createElement("thead"))
    document.getElementById("solution").appendChild(table2)

    let trs = tbody.getElementsByTagName("tr");
    let counter = 0
    for (let item of rows) {

        let currElement = trs.item(counter).getAttribute("dataelement")
        //alert("Row: "+currElement)
        let cell = trs.item(counter).insertCell()
        cell.style.width = "500px"
        getComponentsListForElement(currElement)

        let elementMission = trs.item(counter).cells.item(0).innerText

        let element_exists = false
        let rowspan =  0
        for (let key in componentsOfElements[currElement])
            rowspan++;
        for (let key in componentsOfElements[currElement])
        {
            console.log("Component of element: "+componentsOfElements[currElement][key])

            let component_mission = componentsOfElements[currElement][key].mission
            let component_name = componentsOfElements[currElement][key].name

            /*for (component_name in componentsOfElements[currElement][key])
            {
                component_mission = componentsOfElements[currElement][key][component_name]
                break
            }*/

            let rows2 = (table2.insertRow());
            if (!element_exists)
            {
                let cell2 = rows2.insertCell()
                cell2.style.width = "500px"
                cell2.innerText = elementMission
                cell2.rowSpan = rowspan
                element_exists = true
            }

            let cell22 = rows2.insertCell()
            cell22.style.width = "500px"
            cell22.innerText = component_mission//componentsOfElements[currElement][key]
            let cell2_select = rows2.insertCell()
            cell2_select.style.width = "500px"

            let cell2_paramName = rows2.insertCell()
            cell2_paramName.style.width = "500px"
            cell2_paramName.innerText = currElement+"_"+component_name
            cell2_paramName.hidden = true
            //param_cells.push(cell2_paramName)

            let cell2_type = rows2.insertCell()
            cell2_type.style.width = "500px"
            let select_Type =document.createElement("select")
            select_Type.id = "select-type-"+currElement+"-"+component_name

            let optionNotSelect = document.createElement("option")
            optionNotSelect.text = "..."
            optionNotSelect.selected = true
            optionNotSelect.disabled = true
            select_Type.add(optionNotSelect)

            for (let type_name in types_map)
            {
                let option_type = document.createElement("option")
                option_type.text = types_map[type_name]
                option_type.value = type_name
                select_Type.options.add(option_type)
            }

            let alertType = document.createElement("div")
            alertType.classList.add("alert")
            alertType.id = "type-"+currElement+"-"+component_name+"block"

            select_Type.setAttribute("onchange", "$.post('/answer/5/', 'student="+studentID+"&parameterName=" + currElement+"_"+component_name + "&datatype='+this.value, function (data) { obj = (data); setCorrectForBlock('"+alertType.id+"', (obj.correct === 'true'), obj.message); if (obj.correct === 'true') {document.getElementById('"+select_Type.id+"').disabled=true; document.getElementById('"+alertType.id+"').hidden=true; }  }); setParamType('"+ currElement+"_"+component_name +"', this.options[this.options.selectedIndex].text);");

            cell2_type.appendChild(select_Type)
            cell2_type.appendChild(alertType)



            cell2_type.hidden = true
            type_cells.push(cell2_type)

            let select = document.createElement("select")


            let optionReturn = document.createElement("option")
            optionReturn.value = "return"
            optionReturn.text = dic["text-return_value"]

            let optionInputParam = document.createElement("option")
            optionInputParam.value = "read-only"
            optionInputParam.text = dic["text-input_parameter"]

            let optionOutputParam = document.createElement("option")
            optionOutputParam.value = "write-only"
            optionOutputParam.text = dic["text-output_parameter"]

            let optionUpdatableParam = document.createElement("option")
            optionUpdatableParam.value = "read-write"
            optionUpdatableParam.text = dic["text-updatable_parameter"]


            let optionNotSelect1 = document.createElement("option")
            optionNotSelect1.text = "..."
            optionNotSelect1.disabled = true
            optionNotSelect1.selected = true
            select.options.add(optionNotSelect1)
            select.options.add(optionInputParam)
            select.options.add(optionUpdatableParam)
            select.options.add(optionOutputParam)
            select.options.add(optionReturn)

            cell2_select.appendChild(select)

            let alert = document.createElement("div")
            alert.classList.add("alert")
            alert.innerText = "Info"
            alert.hidden = true
            alert.id = currElement+"-"+component_name+"-organization"
            alert.style.width = "320px"
            alert.style.margin = "20px"
            cell2_select.appendChild(alert)
            select.id = "select-organization-"+currElement+"-"+component_name
            let onchange = "$.post('/answer/4/', 'student="+studentID+"&elementName="+currElement+"&componentName="+component_name+"&transferMethod='+this.value, function (data) {console.log(data); obj = (data); setCorrectForBlock('" + alert.id + "', (obj.correct === 'true'), obj.message); if (obj.correct === 'true') {  addParamNamesIfNecessary(); document.getElementById('"+alert.id+"').hidden=true; document.getElementById('"+select.id+"').disabled=true; } }); choseOrganization('" + currElement + "', '" + component_name + "', this.value);"
            select.setAttribute("onchange", onchange)

            let p = document.createElement("p")
            p.id = "parameter-name-"+currElement+"-"+component_name
            cell2_select.appendChild(p)

            cell.innerText += component_mission + '\n';

        }
        counter++
    }

    let thead = table2.getElementsByTagName("thead").item(0);
    thead.appendChild(document.createElement("tr"));
    let theadtr = thead.getElementsByTagName("tr").item(0)
    theadtr.innerHTML += "<th scope='col'>"+dic["text-data_element"]+"</th>" + "<th>"+ dic["text-data_component"] +"</th>" + "<th>" + dic["text-transfer"] + "</th>"

}

function setCorrectForBlock(blockID, correct, message)
{

    //message = message.replace(/\\/g, '');

    let block = document.getElementById(blockID)
    block.hidden = false
    //block.innerText = message.replace(/\\/g, '')
    if (correct)
    {
        block.classList.add("alert-success")
        block.classList.remove("alert-danger")
    }
    else
    {
        block.classList.remove("alert-success")
        block.classList.add("alert-danger")
    }

}

paramsDirections = {};

function getParameterName(element, component)
{
    if (componentsOfElements[element].length === 1)
        return element;
    return  element+"_"+component
}

function choseOrganization(elementName, componentName, organization)
{
    let paramName = getParameterName(elementName, componentName)
    if (organization == "read-only" || organization == "write-only" || organization == "read-write")
    {
        let alertTypeId = "type-"+elementName+"-"+componentName+"block"
        let select_TypeId = "select-type-"+elementName+"-"+componentName
        document.getElementById("parameter-name-"+elementName+"-"+componentName).innerText = "Parameter name: " + paramName
        let onchange = "$.post('/answer/5', 'student="+studentID+"&parameterName="+paramName+"&datatype='+this.value, function (data) { obj = (data); setCorrectForBlock('"+alertTypeId+"', (obj.correct === 'true'), obj.message); if (obj.correct === 'true') {document.getElementById('"+select_TypeId+"').disabled=true; document.getElementById('"+alertTypeId+"').hidden=true; }  }); setParamType('"+ paramName +"', this.options[this.options.selectedIndex].text);"
        let select = document.getElementById("select-type-"+ elementName +"-"+ componentName)
        select.setAttribute("onchange", onchange)
    }
    if (organization == "return")
    {
        let alertTypeId = "type-"+elementName+"-"+componentName+"block"
        let select_TypeId = "select-type-"+elementName+"-"+componentName
        let onchange = "$.post('/answer/5', 'student="+studentID+"&parameterName=return&datatype='+this.value, function (data) { obj = (data); setCorrectForBlock('"+alertTypeId+"', (obj.correct === 'true'), obj.message); if (obj.correct === 'true') {document.getElementById('"+select_TypeId+"').disabled=true; document.getElementById('"+alertTypeId+"').hidden=true; }  }); setParamType('"+ paramName +"', this.options[this.options.selectedIndex].text);"
        let select = document.getElementById("select-type-"+ elementName +"-"+ componentName)
        select.setAttribute("onchange", onchange)
    }
    if (!componentsOfElementsWithOrganization.hasOwnProperty(elementName))
        componentsOfElementsWithOrganization[elementName] = {}
    componentsOfElementsWithOrganization[elementName][componentName] = true;
    switch (organization)
    {
        case "read-only":
            paramsDirections[paramName] = "in"
            break;
        case "read-write":
            paramsDirections[paramName] = "in|out"
            break;
        case "write-only":
            paramsDirections[paramName] = "out"
            break;
        case "return":
            paramsDirections[paramName] = "return"
            break;
    }
}

function allComponentsHasOrganization()
{
    /*
    for (let elementName in componentsOfElements)
    {
        if (!componentsOfElementsWithOrganization.hasOwnProperty(elementName))
            return false;

        let i=0;
        for (let componentName in componentsOfElements[elementName])
        {
            if (! componentsOfElementsWithOrganization[elementName][i].name === componentName)
            {
                return false;
            }
            i++
        }
    }
    return true;
    */
    return false;
}

function addParamNamesIfNecessary()
{
    if (allComponentsHasOrganization())
    {
        for (let cell in param_cells)
        {
            param_cells[cell].hidden = false;
        }
        for (let cell in type_cells)
        {
            type_cells[cell].hidden = false;
        }
        let tr = document.getElementsByTagName("table").item(1).getElementsByTagName("thead").item(0).getElementsByTagName("tr").item(0)
        tr.innerHTML += "<th>" + "Type of C-language" + "</th>"
    }
}

function setParamType(paramName, paramType)
{
    param_types[paramName] = paramType;
}

function getDoxygenComment()
{
    let comment = "/*!\n"
    let returnString = ""
    for (let paramName in param_types)
    {
        if (paramsDirections[paramName] != "return")
            comment+="\\param [" + paramsDirections[paramName] + "] " + paramName + ": " + param_types[paramName] + "\n"
        else
            returnString  = "\\return: " + param_types[paramName]
    }
    if (returnString != "")
        comment += returnString + "\n"
    comment += "\n*/"
    return comment
}

function showPrototypeBlock()
{
    let block = document.createElement("div")
    block.classList.add("card")
    block.style.margin = "20px"
    block.style.padding = "20px"

    document.body.appendChild(block)
    let title = document.createElement("div")
    title.classList.add("card-title")
    title.innerText = dic["text-create_function_prototype"]
    block.appendChild(title)

    let funcName = document.createElement("p")
    funcName.innerHTML = "Function name: <b>distance_between_dates</b>"
    block.appendChild(funcName)

    let body = document.createElement("p")
    body.classList.add("card-body")
    body.innerText = "Doxygen-comment: \n " + getDoxygenComment()

    let p = document.createElement("p")
    p.innerText = dic["text-function_prototype"]
    let prototype = document.createElement("div")
    prototype.id = "prototype-code"
    prototype.style.fontFamily = "Courier New"
    prototype.style.fontSize = "xx-large"
    prototype.classList.add("alert")
    prototype.classList.add("alert-primary")
    body.appendChild(p)
    body.appendChild(prototype)


    let removeLastLexem = document.createElement("button")
    removeLastLexem.classList.add("btn")
    removeLastLexem.classList.add("btn-danger")
    removeLastLexem.innerText = dic["text-remove_last_lexem"]
    removeLastLexem.id = "remove-last-lexem"
    removeLastLexem.hidden = true
    removeLastLexem.setAttribute("onclick", "$.post('api.php', 'student='+studentID+'&interaction=6&action=pop', function (data) {obj = JSON.parse(data); setCorrectForBlock('prototype-errors', (obj.correct === 'true'), obj.message); document.getElementById('prototype-code').innerText = obj.code; enableLexemButtons();})")
    body.appendChild(removeLastLexem)

    let errors = document.createElement("div")
    errors.classList.add("alert")
    errors.id = "prototype-errors"
    errors.style.margin = "20px"

    body.appendChild(errors)

    let lexems = document.createElement("div")
    lexems.id = "lexem-buttons"
    body.appendChild(lexems)

    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'getPrototypeLexems.php', false);
    xhr.send();


    if (xhr.status != 200) {
        // обработать ошибку
        alert( xhr.status + ': ' + xhr.statusText );
    } else {
        var lexemsObjects = JSON.parse(xhr.responseText);
        for (let index in lexemsObjects)
        {
            let obj = lexemsObjects[index]
            //alert(obj)
            let btn = document.createElement("button")
            btn.classList.add("btn")
            btn.classList.add("btn-primary")
            btn.innerText = obj.value
            btn.style.margin = "20px"
            let request = "student='+studentID+'&interaction=6&action=push&lexemType=" + obj.type + "&lexemValue=" + obj.value
            let onclick = "$.post('api.php', '" + request + "', function(data) {obj = JSON.parse(data); document.getElementById('prototype-code').innerText = obj.code;  setCorrectForBlock('prototype-errors', obj.correct === 'true', obj.message); enableLexemButtons(obj.correct === 'true')})"
            btn.setAttribute("onclick", onclick)
            lexems.appendChild(btn)
        }
    }

    block.appendChild(body)

}

function enableLexemButtons(enable = true)
{
    let removeLastLexem = document.getElementById("remove-last-lexem")
    removeLastLexem.hidden = enable
    let block = document.getElementById("lexem-buttons")
    let childs = block.childNodes

    childs.forEach((value, key, parentNode) => {value.disabled = !enable})
}