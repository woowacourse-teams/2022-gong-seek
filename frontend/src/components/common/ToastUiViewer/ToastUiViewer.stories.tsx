import ToastUiViewer, {
	ToastUiContentProps,
} from '@/components/common/ToastUiViewer/ToastUiViewer';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'common/ArticleContent/ToastUiViewer',
	component: ToastUiViewer,
} as Meta;

const Template: Story<ToastUiContentProps> = (args) => <ToastUiViewer {...args} />;
export const DefaultViewer = Template.bind({});
DefaultViewer.args = {
	initContent: '<h2>제목</h2>',
};
