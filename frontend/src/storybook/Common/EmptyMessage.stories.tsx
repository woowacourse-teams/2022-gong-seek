import EmptyMessage from '@/components/@common/EmptyMessage/EmptyMessage';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Common/EmptyMessage',
	component: EmptyMessage,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story<{ children: string }> = (args) => <EmptyMessage {...args} />;

export const DefaultEmptyMEssage = Template.bind({});
DefaultEmptyMEssage.args = {
	children: '빈 게시글 입니다',
};
