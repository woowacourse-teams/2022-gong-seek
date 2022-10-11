import VoteGenerateButton from '@/components/vote/VoteGenerateButton/VoteGenerateButton';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Vote/VoteGenerateButton',
	component: VoteGenerateButton,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <VoteGenerateButton {...args} />;

export const DefaultVoteGenerateButton = Template.bind({});
DefaultVoteGenerateButton.args = {
	children: '투표 만들기',
};
