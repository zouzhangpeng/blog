<template>
  <a-modal :closable="false" :footer="false" :title="title" :visible="visible" unmount-on-close>
    <a-form :model="formData" :rules="formRules" layout="vertical" @submit="handleSubmit">
      <a-form-item field="tagName" label="分类名称">
        <a-input v-model="formData.tagName"/>
      </a-form-item>
      <a-form-item field="color" label="颜色">
        <input v-model="formData.color" type="color"/>
      </a-form-item>
      <a-form-item field="enable" label="是否启用" required>
        <a-switch v-model="formData.enable"/>
      </a-form-item>
      <operations-group center>
        <a-button :loading="loading" html-type="submit" type="primary">保存</a-button>
        <a-button type="outline" @click="handleCancel">取消</a-button>
      </operations-group>
    </a-form>
  </a-modal>
</template>

<script lang="ts" setup>
import {type PropType, reactive, ref} from "vue";
import OperationsGroup from "@/components/OperationsGroup/index.vue";
import {Notification, type ValidatedError} from "@arco-design/web-vue";
import {createTag, updateTag} from "@/api/admin/tag-manage";

defineProps({
  visible: {
    type: Boolean,
    required: true
  },
  title: {
    type: String,
    default: ""
  },
  formData: {
    type: Object as PropType<SaveTagForm>,
    default: {}
  }
})

const emit = defineEmits(["cancel"]);

const loading = ref<boolean>(false)

const formRules = reactive({
  tagName: [
    {
      required: true,
      message: "标签名称必填"
    }
  ],
  color: [
    {
      required: true,
      message: "颜色必填"
    }
  ],
  enable: [{
    required: true,
    message: "是否启用必填"
  }]
})

const handleCancel = () => {
  emit("cancel")
}

const handleSubmit = async ({values, errors}: {
  errors: Record<string, ValidatedError> | undefined;
  values: Record<string, any>;
}) => {
  if (loading.value) {
    return
  }
  if (errors) {
    return
  }
  loading.value = true
  try {
    if (values.tagId) {
      await updateTag(values.tagId, values as UpdateTagRequest)
      Notification.success("更新成功");
    } else {
      await createTag(values as CreateTagRequest)
      Notification.success("创建成功");
    }
    emit("cancel", true)
  } finally {
    loading.value = false;
  }
}

</script>

<style scoped>
.button-group {
  border-top: 1px solid var(--color-neutral-3)
}
</style>