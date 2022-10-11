import VoteItem, { VoteItemProps } from '@/pages/Discussion/VoteItem/VoteItem';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Vote/VoteItem',
	component: VoteItem,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story<VoteItemProps> = (args) => <VoteItem {...args} />;

export const FirstVoteItem = Template.bind({});
FirstVoteItem.args = {
	title: '1번 항목',
	totalVotes: 10,
	itemVotes: 7,
};

export const SecondVoteItem = Template.bind({});
SecondVoteItem.args = {
	title: '2번 항목',
	totalVotes: 10,
	itemVotes: 7,
};

export const ThirdVoteItem = Template.bind({});
ThirdVoteItem.args = {
	title: '3번 항목',
	totalVotes: 10,
	itemVotes: 7,
};

export const FourthVoteItem = Template.bind({});
FourthVoteItem.args = {
	title: '4번 항목',
	totalVotes: 10,
	itemVotes: 7,
};

export const FifthVoteItem = Template.bind({});
FifthVoteItem.args = {
	title: '5번 항목',
	totalVotes: 10,
	itemVotes: 7,
};
