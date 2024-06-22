  /*=============== 회원가입 유효성  vaildateForm() js 명시=========================*/
function validateForm(){
			let name = document.getElementById("name").value;
		 	let id = document.getElementById("id").value;
	 		let pwd = document.getElementById("pwd").value;
	 		let pwdCheck = document.getElementById("pwdCheck").value;
	 		let email = document.getElementById("email").value;
	 		let gender = document.getElementById("gender").value;
	 	 	let age = document.getElementById("age").value;
	 		let height = document.getElementById("height").value;	
	 		let weight = document.getElementById("weight").value;
	 		
		 	let nameError = document.getElementById("name_message");
		 	let idError = document.getElementById("id_message");
		 	let pwdError = document.getElementById("pwd_message");
		 	let pwdCheckError = document.getElementById("pwdCheck_message");
		 	let emailError = document.getElementById("email_message");
		 	let genderError = document.getElementById("gender_message");
		 	let ageError = document.getElementById("age_message");
		 	let heightError = document.getElementById("height_message");
		 	let weightError = document.getElementById("weight_message");
	
			nameError.textContent = ""; 
			idError.textContent = ""; 
			pwdError.textContent = ""; 
			pwdCheckError.textContent = ""; 
			emailError.textContent = ""; 
			genderError.textContent = ""; 
		 	ageError.textContent = "";  
		 	heightError.textContent = ""; 
		 	weightError.textContent = ""; 
	
			
		if (name === ""){
			nameError.textContent = "*이름을 입력해주세요.";
		 	// 3초 후에 메시지를 숨기기
		   	setTimeout(function() {
				nameError.textContent='';
		    }, 2000); // 3000 밀리초 = 3초
			return false;
		}	
			
		if (id === ""){
			idError.textContent = "*아이디를 입력해주세요.";
			// 2초 후에 메시지를 숨기기
		   	setTimeout(function() {
				idError.textContent='';
		    }, 2000); 
			return false;
		}
		if (pwd === ""){
			pwdError.textContent = "※비밀번호는 문자, 숫자, 특수문자(~!@#$%^&*)의 조합10 ~16자리로 입력이 가능합니다."
			// 2초 후에 메시지를 숨기기
		   	setTimeout(function() {
				pwdError.textContent='';
		    }, 2000); 
			return false;
		}
		if (pwdCheck === ""){
			pwdCheckError.textContent = "※비밀번호를 확인해주세요";
			// 2초 후에 메시지를 숨기기
		   	setTimeout(function() {
				pwdCheckError.textContent='';
		    }, 2000); 
			return false;
		}
		if (email === ""){
			emailError.textContent = "*이메일를 입력해주세요.";
			// 2초 후에 메시지를 숨기기
		   	setTimeout(function() {
				emailError.textContent='';
		    }, 2000); 
			return false;
		}
		if (gender === ""){
			genderError.textContent = "*성별을 선택해주세요.";
			// 2초 후에 메시지를 숨기기
		   	setTimeout(function() {
				genderError.textContent='';
		    }, 2000); 
			return false;
		}	
			
		if (age === ""){
			ageError.textContent = "*연령을 선택해주세요.";
			// 2초 후에 메시지를 숨기기
		   	setTimeout(function() {
				ageError.textContent='';
		    }, 2000);
			return false;
		}
		if (height === ""){
			heightError.textContent = "※신장을 입력해주세요";
			// 2초 후에 메시지를 숨기기
		   	setTimeout(function() {
				heightError.textContent='';
		    }, 2000); 
			return false;
		}
		if (weight === ""){
			weightError.textContent = "*체중을 입력해주세요.";
			// 2초 후에 메시지를 숨기기
		   	setTimeout(function() {
				weightError.textContent='';
		    }, 2000); 
			return false;
		}
		return true;		
	}
	
	