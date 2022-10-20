import { registerVoteItems } from '@/api/vote';
import Vote from '@/components/vote/Vote/Vote';
import { VoteHandler } from '@/mock/vote';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Vote/index',
	component: Vote,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const StoryVote = ({ articleId }: { articleId: string }) => {
	const items = ['투표 1', '투표 2', '투표 3'];
	registerVoteItems({ items, articleId: '0', expiryDate: '2022-10-21T10:10:00' });

	return <Vote articleId={articleId} />;
};

const Template: Story<{ articleId: string }> = (args) => <StoryVote {...args} />;

export const DefaultVote = Template.bind({});
DefaultVote.args = {
	articleId: '0',
};

DefaultVote.parameters = {
	msw: {
		handlers: VoteHandler,
	},
};
