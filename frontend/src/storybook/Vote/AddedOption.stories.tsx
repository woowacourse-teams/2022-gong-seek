import { PropsWithStrictChildren } from 'gongseek-types';

import AddedOption from '@/pages/VoteGenerator/AddedOption/AddedOption';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Vote/AddedOption',
	component: AddedOption,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story<PropsWithStrictChildren<{ onClick: () => void }, string>> = (args) => (
	<AddedOption {...args} />
);

export const DefaultAddedOption = Template.bind({});
DefaultAddedOption.args = {
	children: '1번 항목',
};

export const DisabledAddedOption = Template.bind({});
DisabledAddedOption.args = {
	children: '2번 항목',
};
