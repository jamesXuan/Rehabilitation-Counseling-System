var imgRegexpStr = "@@img@(((https:|http:|ftp:|rtsp:|mms:)?//)[^\\s@]+)@@";
var imgTemplate = "@@img@${url}@@";
var imgRegexp = new RegExp(imgRegexpStr);
var imgRegexpGlobal = new RegExp(imgRegexpStr, "g");

var _xl_ktFriendListOffcanvas;

var _myToken = function (ignoreDelete) {
  ignoreDelete = (ignoreDelete == undefined ? true : ignoreDelete);
  var member = localStorage.getItem("member");
  if (!member) {
    return null;
  }
  try {
    member = JSON.parse(member);
  } catch (e) {
    member = {};
  }
  member.deleted = (member.deleted == undefined ? true : member.deleted);
  if (ignoreDelete && member.deleted) {
    return null;
  }
  return member.token ? member.token : null;
};

var _myId = function () {
  var member = localStorage.getItem("member");
  if (member == null) {
    return null;
  }
  member = JSON.parse(member);
  return member.id;
};

var _filterHtml = function(text) {
  if (text == null || text == undefined) {
    return null;
  }
  return text.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
}

var _filterContent = function (text, roomId) {
  if (text == null) {
    return null;
  }
  text = _filterHtml(text);
  var globalMatchResult = text.match(imgRegexpGlobal);
  if (globalMatchResult == null) {
    return text;
  }
  globalMatchResult.forEach(function (item) {
    var itemMatchResult = item.match(imgRegexp);
    if (itemMatchResult != null) {
      var html = '<div class="symbol ml-1 mr-1">';
      html +=
        '    <img data-fancybox="' +
        roomId +
        '" role="button" class="h-auto" alt="' +
        itemMatchResult[1] +
        '" src="' +
        itemMatchResult[1] +
        '">';
      html += "</div>";
      text = text.replace(itemMatchResult[0], html);
    }
  });
  return text;
};

var _imgTemplate = function (image) {
  return imgTemplate.replace("${url}", image.url);
};

function _initMyProfile(onSuccess) {
  var myToken = _myToken(false);
  if (myToken == null) {
    return;
  }
  $.post(api_url_prefix + "/api/get-my-profile", JSON.stringify({ token: myToken }), function (response) {
    if (response == null) {
      swal.fire({
        html: "抱歉啦~ 服务器异常，烦请尽快联系客服处理，谢谢你~",
        icon: "error",
        confirmButtonText: "我知道了",
      });
      return;
    }

    response.data && localStorage.setItem("member", JSON.stringify(response.data));
    if (response.code == 0) {
      var kt_quick_user = KTUtil.getById("kt_quick_user");
      var info = response.data;
      if (info.avatar) {
        KTUtil.css(KTUtil.find(kt_quick_user, ".user-profile-avatar-image"), "background-image", "url('" + info.avatar + "')");
      }
      if (info.nickname) {
        KTUtil.find(kt_quick_user, ".user-profile-nickname").innerText = info.nickname;
      }
      if (info.mole <= 50) {
        KTUtil.addClass(KTUtil.find(kt_quick_user, ".user-profile-mole"), 'text-danger');
      } else if (info.mole <= 80) {
        KTUtil.addClass(KTUtil.find(kt_quick_user, ".user-profile-mole"), 'text-warning');
      } else {
        KTUtil.addClass(KTUtil.find(kt_quick_user, ".user-profile-mole"), 'text-success');
      }
      KTUtil.find(kt_quick_user, ".user-profile-mole b").innerText = info.mole;
      KTUtil.find(kt_quick_user, ".user-profile-location").innerText = info.location;
      onSuccess && onSuccess(response);
    } else {
      // localStorage.removeItem("member");
      swal.fire({
        html: response.msg + "<br><br><b>如有异议，请联系客服QQ：2727934139。</b>",
        icon: "error",
        showConfirmButton: false,
        allowOutsideClick: false,
        allowEscapeKey: false
      });
    }
  });
}

var _handleMyProfileForm = function () {
  var validation = FormValidation.formValidation(
    KTUtil.getById("kt_my_profile_form"),
    {
      fields: {
        avatar: {
          validators: {
            notEmpty: {
              message: "头像不能为空",
            },
          },
        },
        nickname: {
          validators: {
            notEmpty: {
              message: "昵称不能为空",
            },
          },
        },
        country: {
          validators: {
            notEmpty: {
              message: "国家不能为空",
            },
          },
        },
        state: {
          validators: {
            region: {
              message: "省份不能为空",
            },
          },
        },
        city: {
          validators: {
            region: {
              message: "城市不能为空",
            },
          },
        },
        gender: {
          validators: {
            notEmpty: {
              message: "性别不能为空",
            },
          },
        },
        age: {
          validators: {
            notEmpty: {
              message: "年龄不能为空",
            },
          },
        },
      },
      plugins: {
        trigger: new FormValidation.plugins.Trigger(),
        // submitButton: new FormValidation.plugins.SubmitButton(),
        //defaultSubmit: new FormValidation.plugins.DefaultSubmit(), // Uncomment this line to enable normal button submit after form validation
        bootstrap: new FormValidation.plugins.Bootstrap(),
        transformer: new FormValidation.plugins.Transformer({
          nickname: {
              notEmpty: function (field, element, validator) {
                  return element.value.trim();
              },
          },
          country: {
              notEmpty: function (field, element, validator) {
                  return element.value.trim();
              },
          },
          city: {
              notEmpty: function (field, element, validator) {
                  return element.value.trim();
              },
          },
        }),
      },
    }
  );

  // 初始化表单
  $("#kt_my_profile_modal").on("show.bs.modal", function (e) {
    var member = JSON.parse(localStorage.getItem("member"));
    $("input[name], select", e.currentTarget).each(function (index, item) {
      var value = member[item.name];
      var value = value == undefined ? "" : value;
      if (item.tagName == 'INPUT') {
        if (item.type == "radio") {
          if (item.value == value) {
            item.checked = "checked";
          } else {
            KTUtil.removeAttr(item, "checked");
          }
        } else {
          item.value = value;
        }
      } else if (item.tagName == 'SELECT') {
        $(item).attr('data-init-value', value);
        return;
      }
    });
    renderSelect2($('#kt_my_profile_modal'));
      // 回显头像
      var container = KTUtil.getById("kt_my_profile_avatar");
      var wrapper = KTUtil.find(container, ".image-input-wrapper");
      KTUtil.css(wrapper, "background-image", "none");
      KTUtil.find(container, "input[name=avatar]").value = "";
      KTUtil.find(container, "input[name=avatar_file]").value = "";
      if (member.avatar != undefined) {
        KTUtil.css(wrapper, "background-image", "url(" + member.avatar + ")");
        KTUtil.find(container, "input[name=avatar]").value = member.avatar;
        KTUtil.find(container, "input[name=avatar_file]").value = "";
      }

      // 头像
      new KTImageInput("kt_my_profile_avatar").on("change", function (imgInput) {
        imageInput = imgInput;
          var fileInput = imgInput.input;
          if (fileInput && fileInput.files) {
            _handleFileUpload(null, fileInput.files, function(response) {
              $('#kt_crop_modal #image').attr('src', response.data.url);
              $('#kt_crop_modal').modal('show');
            });
          }
      });
  });

  // 资料更新表单提交事件
  $("#kt_my_profile_form").on("submit", function (e) {
    e.preventDefault();
    validation.validate().then(function (status) {
      if (status == "Valid") {
        var request = {};
        $(e.currentTarget)
          .serializeArray()
          .forEach(function (item, index) {
            request[item.name] = item.value;
          });
        $.ajax({
          url: api_url_prefix + "/api/update-profile",
          type: "POST",
          data: JSON.stringify(request),
          contentType: "application/json;charset=utf-8",
          dataType: "json",
          success: function (response) {
            if (response.success) {
              localStorage.setItem("member", JSON.stringify(response.data));
              swal.fire({
                title: "保存成功",
                icon: "success",
                confirmButtonText: "我知道了",
              });
              setTimeout(function () {
                location.reload();
              }, 3000);
            }
          },
        });
      }
    });
  });
};

var _handleFileUpload = function (imageInput, files, onSuccess) {
  if (files && files[0]) {
    var file = files[0];
    if (!(file instanceof File)) {
      file = new window.File([file], 'crop_' + _extractFilename(imageInput.input.value), {type: file.type})
    }
    //使用FormData对象发送文件
    var formData = new FormData();
    formData.append("file", file);

    var ajaxParams = {
      url: upload_url,
      type: "POST",
      data: formData,
      processData: false,
      contentType: false,
    };
    $.ajax(ajaxParams)
      .done(function (response) {
        if (response.errors.length) {
          swal.fire({
            title: "头像上传失败",
            icon: "error",
            confirmButtonText: "我知道了",
          });
          return;
        }
        onSuccess(response);
        return;
      })
      .fail(function (response) {
        swal.fire({
          title: "头像上传失败",
          icon: "error",
          confirmButtonText: "我知道了",
        });
      });
  }
}

var _handleMatchUpdateForm = function () {
  var _slider_match_age = $("#kt_slider_match_age");
  // custom prefix
  _slider_match_age.ionRangeSlider({
    type: "double",
    grid: false,
    min: 18,
    max: 65,
    prefix: "",
    onChange: function (data) {
      $('#kt_update_match_form input[name="minAge"]').val(data.from);
      $('#kt_update_match_form input[name="maxAge"]').val(data.to);
    },
  });

  var validation = FormValidation.formValidation(
    KTUtil.getById("kt_update_match_form"),
    {
      fields: {
        gender: {
          validators: {
            notEmpty: {
              message: "性别不能为空",
            },
          },
        },
      },
      plugins: {
        trigger: new FormValidation.plugins.Trigger(),
        bootstrap: new FormValidation.plugins.Bootstrap(),
      },
    }
  );

  // 初始化表单
  $("#kt_update_match_modal").on("show.bs.modal", function (e) {
    $.ajax({
      url: api_url_prefix + "/api/get-match-detail",
      type: "POST",
      data: JSON.stringify({ token: _myToken() }),
      contentType: "application/json;charset=utf-8",
      dataType: "json",
      success: function (response) {
        if (response.success) {
          KTUtil.findAll(e.target, "input[name]").forEach(function (item, index) {
            var value = response.data[item.name];
            var value = value == undefined ? "" : value;
            if (item.type == "radio") {
              if (item.value == value) {
                item.checked = "checked";
              } else {
                KTUtil.removeAttr(item, "checked");
              }
            } else {
              item.value = value;
            }
          });
          if (!response.data.genderMatchAvailable) {
            KTUtil.findAll(e.target, "input[name='gender']:not([value='-1'])").forEach(function(item, index) {
              KTUtil.attr(item, 'disabled', 'disabled');
            });
          }
          _slider_match_age.data("ionRangeSlider").update({ from: response.data.minAge, to: response.data.maxAge });
        }
      },
    });

    // validation.validate();
  });

  // 匹配更新表单提交事件
  $("#kt_update_match_form").on("submit", function (e) {
    e.preventDefault();

    validation.validate().then(function (status) {
      if (status == "Valid") {
        var request = {token: _myToken()};
        $(e.currentTarget)
          .serializeArray()
          .forEach(function (item, index) {
            request[item.name] = item.value;
          });
        $.ajax({
          url: api_url_prefix + "/api/update-match",
          type: "POST",
          data: JSON.stringify(request),
          contentType: "application/json;charset=utf-8",
          dataType: "json",
          success: function (response) {
            if (response.success) {
              swal.fire({
                title: "保存成功",
                icon: "success",
                confirmButtonText: "我知道了",
              });
              setTimeout(function () {
                location.reload();
              }, 3000);
            }
          },
        });
      }
    });
  });
};

// 轮询匹配函数
var _applyMatch = function () {
  // console.info("begin applyMatch..." + new Date().getTime());
  if (!window.applyMatch) {
    return;
  }
  var token = _myToken();
  if (token == null) {
    return;
  }
  var msg = {
    memberToken: token,
    operate: MessageEnum.APPLY_MATCH,
    type: MessageType.REQUEST,
  };
  // console.info("send applyMatch..." + new Date().getTime());
  try {
    websocketHeartbeatJs.send(JSON.stringify(msg));
  } catch(e) {
    console.info('send apply match error');
  }
  setTimeout(_applyMatch, 5000);
  // console.info("end applyMatch..." + new Date().getTime());
};

// 开始匹配
var startApplyMatch = function () {
  var currentValue = window.applyMatch == 1;
  window.applyMatch = 1;
  if (currentValue) {
    return;
  }
  // begin
  KTUtil.addClass(KTUtil.getById("kt_start_meet"), "d-none");
  KTUtil.removeClass(KTUtil.getById("kt_matching"), "d-none");
  _applyMatch();
};
// 停止匹配
var stopApplyMatch = function (isManual) {
  window.applyMatch = 0;
  KTUtil.removeClass(KTUtil.getById("kt_start_meet"), "d-none");
  KTUtil.addClass(KTUtil.getById("kt_matching"), "d-none");
  if (isManual) {
    if (_myToken() == null) {
      return;
    }
    var message = {
      memberToken: _myToken(),
      operate: MessageEnum.EXIT,
      type: MessageType.REQUEST,
    };
    // console.info("send exit..." + new Date().getTime());
    websocketHeartbeatJs.send(JSON.stringify(message));
  }
};

var _initTopbar = function () {
  _xl_ktFriendListOffcanvas = new KTOffcanvas(
    KTUtil.getById("kt_friend_list_offcanvas"), {
      overlay: true,
      baseClass: "offcanvas",
      placement: "right",
      closeBy: "kt_friend_list_offcanvas_close",
      toggleBy: "kt_friend_list_toggle",
    }
  );
};

var _fetchFriendList = function (pageNo) {
  if (pageNo == undefined) {
    pageNo = 1;
  }
  if (_myToken() == null) {
    return;
  }
  $.post(
    api_url_prefix + "/api/get-my-friend-list", JSON.stringify({ token: _myToken(), pageNo: pageNo }), function (response) {
      if (response.code != 0) {
        return;
      }
      var finalHtml = "";
      var hasNewMessage = false;
      response.data.records.forEach(function (item, i) {
        if (item.unreadMessageCount > 0) {
          hasNewMessage = true;
        }
        var html = '<div class="d-flex align-items-center justify-content-between mb-5">';
        html += '    <a data-friend="' + encodeURIComponent(JSON.stringify(item)) + '" href="javascript:void(0);" class="text-dark-75 text-hover-primary font-weight-bold font-size-lg" data-toggle="modal" data-target="#kt_friend_chat_modal">';
        html += '        <div class="d-flex align-items-center">';
        html += '            <div class="symbol symbol-circle symbol-50 mr-3">';
        html += '                <img alt="头像" src="' + item.member2.avatar + '" />';
        html += "            </div>";
        html += '            <div class="d-flex flex-column">';
        html += '                <span class="font-weight-bold ' + (item.zhangzhang ? 'text-danger' : '') + '">' + (_filterHtml(item.member2.nickname) || "无昵称用户") + '</span>';
        html += '                <span class="text-muted font-weight-bold font-size-sm">' + (moment(item.member2.lastActiveTime).from(response.serverTime)) + '</span>';
        html += "            </div>";
        html += '        </div>';
        html += '    </a>';
        html += '   <div class="d-flex flex-column align-items-end">';
        // html += '       <span class="text-muted font-weight-bold font-size-sm">' + (moment(item.member2.lastActiveTime).from(response.serverTime)) + '</span>';
        html += '       <span class="label label-sm label-danger ' + (item.unreadMessageCount > 0 ? '' : 'd-none') + '">' + item.unreadMessageCount + '</span>';
        html += '   </div>';
        html += (item.zhangzhang ? '' : '   <a data-friend="' + encodeURIComponent(JSON.stringify(item)) + '" href="javascript:void(0);" class="btn btn-sm btn-light font-weight-bolder my-lg-0 my-2 py-1 text-dark-50 friend-delete">删除</a>');
        html += '</div>';
        finalHtml += html;
      });
      if (finalHtml == "") {
        finalHtml += "空空如也";
      }
      KTUtil.setHTML(KTUtil.getById("kt_my_friend_list_container"), finalHtml);

      // 提示信息
      if (hasNewMessage) {
        KTUtil.removeClass(KTUtil.find(KTUtil.getById("kt_friend_list_toggle"), ".pulse-ring"), "d-none");
        return;
      }
      KTUtil.addClass(KTUtil.find(KTUtil.getById("kt_friend_list_toggle"), ".pulse-ring"), "d-none");
    }
  );
};

var _fetchState = function() {
  var payload = {
    type: MessageType.REQUEST,
    operate: MessageEnum.STATE,
    memberToken: _myToken()
  };
  websocketHeartbeatJs.send(JSON.stringify(payload));
};

var _initFriendManage = function () {
  $("#kt_friend_chat_modal").on("shown.bs.modal", function (e) {
    // _xl_ktFriendListOffcanvas.hide();
  });

  $("#kt_friend_chat_modal").on("show.bs.modal", function (e) {
    var friendString = KTUtil.attr(e.relatedTarget, "data-friend");
    var friend = JSON.parse(decodeURIComponent(friendString));
    var friendDetail = friend.member2;
    KTUtil.find(e.currentTarget, ".friend-detail-nickname").innerText = friendDetail.nickname || "";
    KTUtil.attr(e.currentTarget, "data-room-id", friend.roomId);
    _fetchMessageList(friend.roomId, e.currentTarget);
  }).on("hide.bs.modal", function(e) {
    KTUtil.find(e.currentTarget, ".friend-detail-nickname").innerText = "";
    KTUtil.attr(e.currentTarget, "data-room-id", '');
    KTLayoutChat._handleResetMessageList(e.currentTarget);
  });

  // 删除好友
  KTUtil.on(KTUtil.getById("kt_friend_list_offcanvas"), ".btn.friend-delete", "click", function (e) {
    var friendString = KTUtil.attr(e.target, "data-friend");
    var friend = JSON.parse(decodeURIComponent(friendString));
    swal.fire({
        title: '确认删除与 ' + (friend.member2.nickname || '无昵称用户') + ' 的好友关系吗？',
        // icon: 'info',
        imageUrl: friend.member2.avatar,
        imageWidth: 120,
        imageHeight: 120,
        imageAlt: "头像裂了",
        showCancelButton: true,
        confirmButtonText: "确认",
        cancelButtonText: "我再想想",
    }).then(function (result) {
        if (result.isConfirmed) {
          $.post(api_url_prefix + "/api/delete-friend", JSON.stringify({ token: _myToken(), friendId: friend.id}), function (response) {
            if (response.success) {
              _fetchFriendList();
              swal.fire({
                title: "你已解除与 " + (friend.member2.nickname || '无昵称用户') + " 的好友关系",
                icon: "success",
                confirmButtonText: "我知道了",
              });
            }
          });
        }
    });
  });
};

var imageInput;
var _initCropModal = function () {
  $("#kt_crop_modal").on("shown.bs.modal", function (e) {
    _initCropper('image');
  }).on('hidden.bs.modal', function(e) {
    imageInput.input.value = '';
    cropper.destroy();
  })

  // 表单
  $('#kt_crop_modal form').on('submit', function(e) {
    e.preventDefault();
    cropper.getCroppedCanvas().toBlob(function(blob) {
      _handleFileUpload(imageInput, [blob], function(response) {
        imageInput.hidden.value = response.data.url;
        KTUtil.css(imageInput.wrapper, "background-image", "url(" + response.data.url + ")");
        KTUtil.addClass(imageInput.element, "image-input-changed");
        KTUtil.removeClass(imageInput.element, "image-input-empty");
        $("#kt_crop_modal").modal('hide');
      });
    }, "image/jpeg", 0.6);
  })
};

var _code2icon = function (code) {
  if (code == 0) {
    return "success";
  } else if (code == 1) {
    return "error";
  } else if (code == 2) {
    return "warning";
  } else if (code == 3) {
    return "info";
  } else if (code == 4) {
    return "question";
  }
  return "info";
};

// 好友申请处理
(function () {
  // 1 好友申请待处理列表
  var friendApplyList = [];
  // 2 好友申请对方处理结果
  var friendApplyResultList = [];
  window._handleFriendApplyList = function (message) {
    friendApplyList = message.data.friendApplyList;
    friendApplyResultList = message.data.friendApplyResultList;
    if (friendApplyList.length || friendApplyResultList.length) {
      KTUtil.removeClass(
        KTUtil.find(
          KTUtil.getById("kt_friend_apply_list_toggle"),
          ".pulse-ring"
        ),
        "d-none"
      );
      return;
    }
    KTUtil.addClass(
      KTUtil.find(KTUtil.getById("kt_friend_apply_list_toggle"), ".pulse-ring"),
      "d-none"
    );
  };

  KTUtil.on(KTUtil.getById("kt_friend_apply_list_toggle"), ".btn", "click", function (e) {
      if (!friendApplyList.length && !friendApplyResultList.length) {
        return;
      }
      if (friendApplyList.length) {
        var first = friendApplyList[0];
        swal.fire({
            title: (first.member1.nickname || "") + " 申请加你为好友",
            // icon: 'info',
            imageUrl: first.member1.avatar,
            imageWidth: 120,
            imageHeight: 120,
            imageAlt: "头像裂了",
            html: '请求消息：</br>' + _filterHtml(first.remark || ''),
            showCancelButton: true,
            confirmButtonText: "同意",
            cancelButtonText: "拒绝",
            reverseButtons: true,
        }).then(function (result) {
            if (result.isConfirmed) {
              $.post(api_url_prefix + "/api/process-friend-apply", JSON.stringify({token: _myToken(), status: 1, memberId: first.member1.id}), function (response) {
                if (response.code != 0) {
                  swal.fire({
                    title: response.msg,
                    icon: "error",
                    confirmButtonText: "我知道了",
                  });
                  return;
                }
                swal.fire({
                  title: "你已同意 " + first.member1.nickname + " 的好友申请",
                  icon: "success",
                  confirmButtonText: "我知道了",
                });
                _fetchFriendList();
                _fetchState();
              });
            } else if (result.dismiss === Swal.DismissReason.cancel) {
              $.post(api_url_prefix + "/api/process-friend-apply", JSON.stringify({ token: _myToken(), status: 2, memberId: first.member1.id }), function () {
                  swal.fire({
                    title: "你已拒绝 " + first.member1.nickname + " 的好友申请",
                    icon: "success",
                    confirmButtonText: "我知道了",
                  });
                  _fetchState();
                }
              );
            }
        });
      } else if (friendApplyResultList.length) {
        var first = friendApplyResultList[0];
        alertOption = {
          imageUrl: first.member2.avatar,
          imageWidth: 120,
          imageHeight: 120,
          confirmButtonText: "我知道了",
        };
        if (first.status == 1) {
          _fetchFriendList();
          alertOption.icon = "success";
          alertOption.title =
            (first.member2.nickname || "") + " 同意了你的好友申请";
        } else if (first.status == 2) {
          alertOption.icon = "error";
          alertOption.title =
            (first.member2.nickname || "") + " 拒绝了你的好友申请";
        }
        swal.fire(alertOption);
        $.post(
          api_url_prefix + "/api/read-friend-apply-result",
          JSON.stringify({ token: _myToken(), id: first.id })
        );
      }
    }
  );
})();

var _initFileDropzone = function (id) {
  // set the dropzone container id
  var idSelector = "#" + id;

  // set the preview element template
  var previewNode = $(idSelector + " .dropzone-item");
  previewNode.id = "";
  var previewTemplate = previewNode.parent(".dropzone-items").html() || "";
  previewNode.remove();

  var myDropzone = new Dropzone(idSelector, {
    // Make the whole body a dropzone
    url: upload_url, // Set the url for your upload script location
    parallelUploads: 1,
    maxFilesize: 30, // Max filesize in MB
    previewTemplate: previewTemplate,
    acceptedFiles: 'image/*',
    //  previewsContainer: id + " .dropzone-items", // Define the container to display the previews
    previewsContainer: false,
    clickable: idSelector + " .dropzone-select", // Define the element that should be used as click trigger to select files.
  });

  // Hide the total progress bar when nothing's uploading anymore
  myDropzone.on("complete", function (progress) {
    if (!progress.accepted) {
      swal.fire({
        title: "暂不支持上传该类型的文件哦~",
        icon: "error",
        confirmButtonText: "我知道了",
        didClose: function () {
          window._hmt && window._hmt.push(['_trackEvent', 'popup', 'close', 'file-type-unsupport', _myToken() || 'none' ]);
        }
      });
      return;
    }
    var responseText = progress.xhr.responseText;
    var response = JSON.parse(responseText);
    if (response.errors.length) {
      // TODO 提示上传失败
      return;
    }
    var imgText = _imgTemplate(response.data);
    var content = KTUtil.find(KTUtil.getById(id), "textarea").value;
    if (content.length) {
      content += "\n";
    }
    content += imgText;
    KTUtil.find(KTUtil.getById(id), "textarea").value = content;
  });

  // 复制图片到textarea
  KTUtil.find(id, 'textarea').onpaste = function(event){
    var clipboardData = (event.clipboardData || event.originalEvent.clipboardData);
    if (clipboardData && clipboardData.files.length > 0 && !KTUtil.hasAttr(event.target, 'disabled') && !KTUtil.hasAttr(event.target, 'readonly')) {
      event.preventDefault();
      var items = clipboardData.items;
      for (index in items) {
        var item = items[index];
        if (item.kind === 'file') {
          // adds the file to your dropzone instance
          myDropzone.addFile(item.getAsFile())
        }
      }
    }
  }
};

var _initFancybox = function () {
  Fancybox.bind("[data-fancybox]");
};

var _fetchMessageList = function (roomId, $element) {
  var token = _myToken();
  if (token == null) {
    return;
  }
  var payload = { token: token };
  if (roomId != null) {
    payload.roomId = roomId;
  }
  $.post(api_url_prefix + "/api/get-message-list", JSON.stringify(payload), function (response) {
      if (response.code != 0) {
        return;
      }
      if (roomId != null) {
        _fetchFriendList();
      }
      KTLayoutChat._handleResetMessageList($element);
      $.each(response.data, function (i, msgData) {
        // console.info(message);
        if (msgData.system) {
          KTLayoutChat.handeSystemMessaging($element, msgData);
        } else if (msgData.memberId != _myId()) {
          msgData.content = _filterContent(msgData.content, roomId);
          KTLayoutChat.handleOtherMessaging($element, msgData);
        } else if (msgData.memberId == _myId()) {
          msgData.content = _filterContent(msgData.content, roomId);
          KTLayoutChat.handleSelfMessaging($element, msgData);
        }
      });
    }
  );
};

var onSend = function (msg) {
  // console.info("sending message: " + JSON.stringify(msg));
  var payload = {
    type: MessageType.REQUEST,
    operate: MessageEnum[msg.operate],
    memberToken: _myToken(),
    roomId: msg.roomId,
  };
  if (msg.content != null) {
    payload.content = msg.content;
  }
  try {
    if (payload.operate == MessageEnum.TYPING) {
      websocketHeartbeatJs.sendTyping(JSON.stringify(payload));
      return;
    }
    websocketHeartbeatJs.send(JSON.stringify(payload));
    return true;
  } catch(e) {
    console.error('send message error');
    if (payload.operate == MessageEnum.TYPING) {
      return false;
    }
    swal.fire({
      width: 460,
      title: "抱歉，消息发送失败了",
      html: "<span class='text-success'><b>请刷新浏览器后重试~</b></span><br><br> <span class='text-danger'><b>友情提示</b>：由于电脑或手机操作系统的限制，<br>当您将浏览器切换到后台(不是退出浏览器哦~)，<br>可能导致您和服务器断开连接哦~",
      icon: "error",
      showCancelButton: true,
      confirmButtonText: "我知道了，立即刷新网页",
      cancelButtonText: "先不刷新网页",
      didClose: function () {
      }
    }).then((result) => {
      if (result.isConfirmed) {
        window._hmt && window._hmt.push(['_trackEvent', 'popup', 'close', 'send-error-and-refresh', _myToken() || 'none' ]);
        setTimeout(function() {
          location.reload(true);
        }, 500)
      } else {
        window._hmt && window._hmt.push(['_trackEvent', 'popup', 'close', 'send-error', _myToken() || 'none' ]);
      }
    });
    return false;
  }
};

var getOnlineCount = function (cb) {
  var _timer = 0;
  var _cb = function () {
    $.post(
      api_url_prefix + "/api/get-online-count",
      JSON.stringify({token: _myToken() }),
      function (res) {
        cb && cb(res);
      }
    );
  };
  var _fn = () => {
    clearTimeout(_timer);
    _cb();
    _timer = setTimeout(_fn, 30000);
  };
  _fn();
};

/**
 * 获取在线人数
 */
function _initOnlineCountTask() {
  getOnlineCount(function (res) {
    if (res.success) {
      KTUtil.getById("text-online-count").innerText = res.data + "人";
    }
  });
}
/**
 * 来新消息，播放音乐
 */
var sound = null;
(function () {
  sound = new Audio(assets_domain + "/assets/media/audio/message.mp3");
  sound.load();
})();
function playNewMessageAudio(force, volume) {
  if (force || document.visibilityState === "hidden" || !document.hasFocus()) {
    if (sound) {
      sound.volume = volume == undefined ? 1 : volume;
      sound.muted = sound.volume <= 0;
      sound.play();
    }
  }
}

var _initPopup = function() {
  var key = 'key.popup.ignore';
  var value = localStorage.getItem(key);
  var popup = true;
  if (value != null) {
    popup = moment().diff(value, 'seconds') >= 0;
  }
  if (popup) {
    swal.fire({
      title: "问题反馈",
      html: '有问题随时联系客服（<b>仅限注册用户</b>），<br>路径：右上角【我的资料】->【在线客服】，<br>我们看到会尽快回复哦~<br>未注册用户问题反馈，请联系客服QQ：2727934139',
      // icon: "info",
      confirmButtonText: "我知道了，3天内不再弹出",
      didClose: function () {
        window._hmt && window._hmt.push(['_trackEvent', 'popup', 'close', 'support', _myToken() || 'none' ]);
      }
    });
    localStorage.setItem(key, moment().add(3, 'days').format('yyyy-MM-DD HH:mm:ss'));
  }
}

var _initSigninPlugin = function() {
  var calendarId = 'idCalendar';

  var initSignin = function (response) {
    var isSign = false;
    var myday = response.data.map(item => new Date(item.date + ' 00:00:00').getTime() / 1000); //已签到的数组
    var options = {
      qdDay: myday,
      onToday: function(o) {
        o.className = "onToday";
      },
      onSignIn: function (){
        KTUtil.find(KTUtil.getById('kt_signin_modal'), '.tips').innerHTML = '今天已签到，将积累您的信誉值~';
        KTUtil.removeClass(KTUtil.find(KTUtil.getById('kt_signin_modal'), '.signBtn'), 'cursor-pointer');
        $$("sign-txt").innerHTML = '已签到';
      },
      onFinish: function() {
        $$("sign-count").innerHTML = myday.length //已签到次数
        $$("idCalendarYear").innerHTML = this.Year;
        $$("idCalendarMonth").innerHTML = this.Month; //表头年份

      }
    };
    var calendar = new Calendar(calendarId, options);
    $$("idCalendarPre").onclick = function() {
      calendar.PreMonth();
    }
    $$("idCalendarNext").onclick = function() {
      calendar.NextMonth();
    }
    //添加今天签到
    $$("signIn").onclick = function() {
      if(isSign == false) {
        var res = calendar.SignIn();
        if(res == '1') {
          $$("sign-txt").innerHTML = '已签到';
          $$("sign-count").innerHTML = parseInt($$("sign-count").innerHTML) + 1;
          isSign = true;
          $.post(api_url_prefix + '/api/signin', JSON.stringify({token: _myToken()}), () => options.onSignIn());
        } else if (res == '2'){
          $$("sign-txt").innerHTML = '已签到';
          // swal.fire({
          //   title: "今天已经签到了",
          //   icon: "success",
          //   confirmButtonText: "我知道了",
          // });
        }
      } else {
        // swal.fire({
        //   title: "今天已经签到了",
        //   icon: "success",
        //   confirmButtonText: "我知道了",
        // });
      }
  
    }
  }

  $("#kt_signin_modal").on("shown.bs.modal", function (e) {
    $.post(api_url_prefix + "/api/signin/list", JSON.stringify({ token: _myToken() }), function (response) {
      if (response.code == 0) {
        initSignin(response);
      } else {
        // error
      }
    });
  });
}

// Private functions
var cropper;
var _initCropper = function(imageElementId) {
  var image = document.getElementById(imageElementId);
  var options = {
    viewMode: 1,
    aspectRatio: 1,
    cropBoxResizable: false,
    dragMode: 'move'
  };
  // https://www.cnblogs.com/eightFlying/p/cropper-demo.html
  if (cropper != null) {
    cropper.destroy();
  }
  cropper = new Cropper(image, options);
};


/**
 * 根据路径解析文件名
 */
var _extractFilename = function(path) {
  var x;
  x = path.lastIndexOf('\\');
  if (x >= 0) // Windows-based path
    return path.substr(x + 1);
  x = path.lastIndexOf('/');
  if (x >= 0) // Unix-based path
    return path.substr(x + 1);
  return path; // just the filename
}

var _initStatus = function() {
  $.getJSON(assets_domain + 'api/status.json?_t=' + (new Date()).getTime(), function(response) {
    if (response.data.swal) {
      setTimeout(function() {
        var key = 'key.status';
        var value = localStorage.getItem(key);
        var show = (value == null ? true : moment().diff(value, 'seconds') >= 0);
        if (show) {
          swal.close();
          swal.fire(response.data.swal.options);
          localStorage.setItem(key, response.data.swal.nextShowTime);
        }
      }, response.data.swal.delayMillis);
    }
  });
}

var _initDisableDevtool = function() {
  var tk = KTUtil.getURLParam("ddtk") || '';
  if (DisableDevtool.md5(tk) != '11d26cc2441c69caada558164a9c7e59') {
    DisableDevtool({
      clearLog: true,
      disableSelect: false,
      disableCopy: false,
      disableCut: false,
      ondevtoolopen: function(detectorType) {
        console.info('ondevtoolopen - detectorType: ' + detectorType);
      },
      ondevtoolclose: function(detectorType) {
        console.info('ondevtoolclose - detectorType: ' + detectorType);
      }
    });
  } else {
    var disableDevtoolConfigStr = KTUtil.getURLParam("disableDevtoolConfig");
    if (disableDevtoolConfigStr == '{}') {
      disableDevtoolConfigStr = JSON.stringify({
        disableMenu: false,
        clearLog: false,
        disableSelect: false,
        disableCopy: false,
        disableCut: false
      });
    }
    var disableDevtoolConfig = JSON.parse(disableDevtoolConfigStr);
    if (disableDevtoolConfig == null) {
      return;
    }
    DisableDevtool(disableDevtoolConfig);
  }
};

var _initMyFriendListPage = function(element) {
  var pageNo = 1;
  element.addEventListener('ps-y-reach-end', function() {
    _fetchFriendList(++pageNo);
  });
};

var _fetchCustomerServiceMessageList = function ($element) {
  var token = _myToken();
  if (token == null) {
    return;
  }
  var payload = { token: token };
  $.post(api_url_prefix + "/api/get-my-feedback-message-list", JSON.stringify(payload), function (response) {
      if (response.code != 0) {
        return;
      }
      KTLayoutChat._handleResetMessageList($element);
      $.each(response.data, function (i, msgData) {
        // console.info(message);
        if (msgData.operate == MessageEnum.FEEDBACK_MESSAGE) {
          msgData.content = _filterContent(msgData.content, 'feedback');
          KTLayoutChat.handleSelfMessaging($element, msgData);
        } else if (msgData.operate == MessageEnum.FEEDBACK_REPLY_MESSAGE) {
          msgData.content = _filterContent(msgData.content, 'feedback');
          KTLayoutChat.handleOtherMessaging($element, msgData);
        }
      });
    }
  );
};

var _initCustomerService = function () {
  $("#kt_feedback_modal").on("show.bs.modal", function (e) {
    _fetchCustomerServiceMessageList(e.currentTarget);
  }).on("hide.bs.modal", function(e) {
    KTLayoutChat._handleResetMessageList(e.currentTarget);
  });
};

var _changeVerifyImage = function(event) {
  $.post(api_url_prefix + "/api/verify-token", JSON.stringify({token: _myToken()}), function(response) {
    if (response.code != 0) {
      swal.fire({
        title: response.msg,
        icon: "error",
        confirmButtonText: "我知道了"
      });
      event && event.preventDefault();
      return;
    }
    KTUtil.attr(KTUtil.find(KTUtil.getById('kt_register_modal'), '.verify-image'), 'src', api_url_prefix + '/api/verify-code?verifyToken=' + response.data.verifyToken);
    KTUtil.attr(KTUtil.find(KTUtil.getById('kt_register_modal'), '[name="verifyToken"]'), 'value', response.data.verifyToken);
  });
};

  /**
   * Index页面
   */
var _handleRegisterForm = function() {
  // Init form validation rules. For more info check the FormValidation plugin's official documentation:https://formvalidation.io/
  var validation = FormValidation.formValidation(KTUtil.getById("kt_register_form"), {
    fields: {
      country: {
        validators: {
          notEmpty: {
            message: "国家不能为空",
          },
        },
      },
      state: {
        validators: {
          region: {
            message: '省份不能为空'
          }
        },
      },
      city: {
        validators: {
          region: {
            message: '城市不能为空'
          }
        },
      },
      gender: {
        validators: {
          notEmpty: {
            message: "性别不能为空",
          },
        },
      },
      age: {
        validators: {
          notEmpty: {
            message: "年龄不能为空",
          },
        },
      },
      verifyCode: {
        validators: {
          notEmpty: {
            message: "验证码不能为空",
          },
        },
      },
      accept: {
        validators: {
          notEmpty: {
            message: "请先同意服务条款",
          }
        }
      }
    },
    plugins: {
      trigger: new FormValidation.plugins.Trigger(),
      // submitButton: new FormValidation.plugins.SubmitButton(),
      //defaultSubmit: new FormValidation.plugins.DefaultSubmit(), // Uncomment this line to enable normal button submit after form validation
      bootstrap: new FormValidation.plugins.Bootstrap(),
    },
  });

  $("#kt_start_meet_button").on("click", function (e) {
    e.preventDefault();
    if (_myToken() != null) {
      startApplyMatch();
      return;
    }
    $("#kt_register_modal").modal('show');
  });

  $("#kt_cancel_match").on("click", function (e) {
    stopApplyMatch(true);
  });

  $("#kt_register_form").on("submit", function (e) {
    e.preventDefault();
    validation.validate().then(function (status) {
      if (status == "Valid") {
        var request = {};
        $(e.currentTarget)
          .serializeArray()
          .forEach(function (item, index) {
            request[item.name] = item.value;
          });
        // 注册的时候，如果本地有member，则增加一个toast提示
        if (_myToken()) {
          swal.fire({
            title: "注册成功",
            icon: "success",
            confirmButtonText: "我知道了",
            didClose: function () {
              location.reload(true);
            },
          });
          return false;
        }
        request.websiteLocation = JSON.stringify(location);
        $.post(
          api_url_prefix + "/api/register",
          JSON.stringify(request),
          function (response) {
            if (response.success) {
              localStorage.setItem("member", JSON.stringify(response.data));
              swal.fire({
                title: "注册成功",
                icon: "success",
                confirmButtonText: "我知道了",
                didClose: function () {
                  location.reload(true);
                },
              });
            } else {
              swal.fire({
                title: response.msg,
                icon: "error",
                confirmButtonText: "我知道了"
              });
              _changeVerifyImage();
            }
          }
        );
      }
    });
  });

  // 设置按钮
  if (_myToken() != null) {
    var needLoginElementList = KTUtil.findAll(KTUtil.getByClass('topbar')[0], '.need-login');
    for (let index = 0; index < needLoginElementList.length; index++) {
      var element = needLoginElementList[index];
      KTUtil.removeClass(element, "d-none");
    }
  }
};

var _initRegisterModal = function() {
  $('#kt_register_modal').on('show.bs.modal', function(event) {
    _changeVerifyImage(event);
  }).on('hide.bs.modal', function(event) {
    KTUtil.attr(KTUtil.find(event.currentTarget, '.verify-image'), 'src', '');
  });
  KTUtil.on(KTUtil.getById('kt_register_modal'), '.verify-image', 'click', _changeVerifyImage);
};

var _customValidator = function() {
  FormValidation.validators.region = function () {
    return {
      validate: function (input) {
        if (!input.element.children.length) {
          return {
            valid: true
          };
        }
        const value = input.value;
        if (value === '') {
          return {
            valid: false
          };
        }
        return {
          valid: true,
        };
      }
    };
  };
};

var renderSelect2 = function(element) {
  var language = {
    errorLoading: function () {
      return '无法载入结果。';
    },
    inputTooLong: function (args) {      
      return '请删除' + (args.input.length - args.maximum) + '个字符';
    },
    inputTooShort: function (args) {            
      return '请再输入至少' + (args.minimum - args.input.length) + '个字符';
    },
    loadingMore: function () {
      return '载入更多结果…';
    },
    maximumSelected: function (args) {
      return '最多只能选择' + args.maximum + '个项目';;
    },
    noResults: function () {
      return '未找到结果';
    },
    searching: function () {
      return '搜索中…';
    },
    removeAllItems: function () {     
      return '删除所有项目';
    }
  };
  var select2 = {
    theme: 'default w-100',
    allowClear: true,
    placeholder: "请选择",
    language: language
  };
  $('form .select2', element).select2(select2);
  $.post(api_url_prefix + '/api/get-country-list', JSON.stringify({}), function(countryListResponse) {
    if (countryListResponse.code != 0) {
      return;
    }
    $('.country', element).html('<option value="" class="d-none"></option>');
    countryListResponse.data.forEach(function(item, index) {
      $('.country', element).append('<option value="' + item.name + '" >' + item.name + '</option>')
    });
    $('.country', element).off('select2:select').on('select2:select', function(event) {
      $.post(api_url_prefix + '/api/get-country-detail', JSON.stringify({name: $(this).val()}), function(response) {
        console.info(response);
        if (response.code != 0) {
          return;
        }
        $('.state, .city', element).html('');
        var stateList = JSON.parse(response.data.state);
        // 有省份的情况
        $('.state', element).prop("disabled", stateList.length <= 1);
        if (stateList.length > 1) {
          // 渲染state
          $('.state', element).html('<option value="" class="d-none"></option>');
          stateList.forEach(function(item, index) {
            $('.state', element).append('<option value="' + item["-Name"] + '" >' + item["-Name"] + '</option>')
          });
          $('.state', element).select2(select2).off('select2:select').on('select2:select', function(event) {
            // 渲染city
            $('.city', element).html('<option value="" class="d-none"></option>');
            // change事件
            var name = $(this).val();
            var cityList = stateList.filter(item => item["-Name"] == name)[0].City;
            $('.city', element).prop("disabled", cityList.length <= 0);
            cityList.forEach(function(cityItem, index) {
              $('.city', element).append('<option value="' + cityItem["-Name"] + '" >' + cityItem["-Name"] + '</option>')
            });
            if ($('.city', element).attr('data-init-value')) {
              $('.city', element).val($('.city', element).attr('data-init-value')).removeAttr('data-init-value').trigger('change').trigger('select2:select');
            }
          }).on('select2:clear', function(event) {
            $('.city', element).html('<option value="" class="d-none"></option>');
          });
          if ($('.state', element).attr('data-init-value')) {
            $('.state', element).val($('.state', element).attr('data-init-value')).removeAttr('data-init-value').trigger('change').trigger('select2:select');
          }
        } else if (stateList.length == 1) {
          // 渲染city
          $('.city', element).html('<option value="" class="d-none"></option>');
          var cityList = stateList[0].City;
          $('.city', element).prop("disabled", cityList.length <= 0);
          cityList.forEach(function(cityItem, index) {
            $('.city', element).append('<option value="' + cityItem["-Name"] + '" >' + cityItem["-Name"] + '</option>')
          });
          $('.city', element).val($('.city', element).attr('data-init-value')).removeAttr('data-init-value').trigger('change').trigger('select2:select');
        } else if (stateList.length == 0) {
          // 渲染city
          $('.city', element).html('<option value="" class="d-none"></option>');
          $('.city', element).prop("disabled", true);
        }
      });
    });
    if ($('.country', element).attr('data-init-value')) {
      $('.country', element).val($('.country', element).attr('data-init-value')).removeAttr('data-init-value').trigger('change').trigger('select2:select');
    }
  });
};

var _initLocation = function() {

  if (_myId() == null) {
    renderSelect2($('#kt_register_modal'));
  }
};

var _initVisibilityChange = function() {
  if (!KTUtil.isMobileDevice()) {
    return;
  }
  var prevHiddenTime = null;
  document.addEventListener("visibilitychange", function() {
    if (document.visibilityState == 'hidden') {
      prevHiddenTime = moment().format('YYYY-MM-DD HH:mm:ss');
      return;
    }
    var hiddenSeconds = moment().diff(prevHiddenTime, 'seconds');
    if (hiddenSeconds >= 10) {
      websocketHeartbeatJs.reconnect();
      _fetchMessageList(null, KTUtil.getById("kt_chat_content"));
      if (($("#kt_friend_chat_modal").attr('data-room-id') || '') != '') {
        _fetchMessageList($("#kt_friend_chat_modal").attr('data-room-id'), KTUtil.getById("kt_friend_chat_modal"));
      }
      if (($("#kt_feedback_modal").data('bs.modal') || {})._isShown) {
        _fetchCustomerServiceMessageList(KTUtil.getById("kt_feedback_modal"));
      }
    }
  });
};

var _initMessageNotifyAudio = function() {
  function initPlayAudio(event) {
    if (!event.isTrusted) {
      return;
    }
    playNewMessageAudio(true, 0);
    document.removeEventListener('click', initPlayAudio);
  };
  document.addEventListener('click', initPlayAudio);
}

var _handleReportForm = function() {
  // Init form validation rules. For more info check the FormValidation plugin's official documentation:https://formvalidation.io/
  var validation = FormValidation.formValidation(KTUtil.getById("kt_report_form"), {
    fields: {
      type: {
        validators: {
          notEmpty: {
            message: "举报分类不能为空",
          },
        },
      },
      remark: {
        validators: {
          notEmpty: {
            message: '举报原因不能为空'
          },
          stringLength: {
            min: 5,
            message: "举报原因不能少于5个字"
          }
        },
      },
    },
    plugins: {
      trigger: new FormValidation.plugins.Trigger(),
      bootstrap: new FormValidation.plugins.Bootstrap(),
    },
  });

  $("#kt_report_form").on("submit", function (e) {
    e.preventDefault();
    validation.validate().then(function (status) {
      if (status == "Valid") {
        var request = {};
        $(e.currentTarget).serializeArray().forEach(function (item, index) {
            request[item.name] = item.value;
        });
        if (_myToken() == null) {
          return;
        }
        request.token = _myToken();
        $.post(api_url_prefix + "/api/report", JSON.stringify(request), function (response) {
            if (response.code == 0) {
              swal.fire({
                title: "举报成功",
                icon: "success",
                confirmButtonText: "我知道了",
                didClose: function () {
                },
              });
            } else {
              swal.fire({
                title: response.msg,
                icon: "error",
                confirmButtonText: "我知道了",
                didClose: function () {
                },
              });
            }
          }
        );
      }
    });
  });
};

var _initReportModal = function() {
  $('#kt_report_modal').on('show.bs.modal', function(event) {
    
  }).on('hide.bs.modal', function(event) {

  });
};

var _initReplaceAllFunction = function() {
  if (''.replaceAll) {
    return;
  }
  function escapeRegExp(string) {
    return string.replace(/[.*+?^${}()|[\]\\]/g, '\\$&'); // $& means the whole matched string
  }
  String.prototype.replaceAll = function(find, replace) {
    return this.replace(new RegExp(escapeRegExp(find), 'g'), replace);
  }
};