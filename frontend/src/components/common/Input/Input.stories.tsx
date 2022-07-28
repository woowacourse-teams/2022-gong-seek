import Input from '@/components/common/Input/Input';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'common/Input',
	component: Input,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <Input {...args} type="text" />;

export const DefaultInput = Template.bind({});
DefaultInput.args = {
	placeholder: '항목을 입력해주세요',
};

export const DisabledInput = Template.bind({});
DisabledInput.args = {
	placeholder: '항목을 입력해주세요',
	disabled: true,
};
