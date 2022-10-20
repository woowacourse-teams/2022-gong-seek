import { BrowserRouter } from 'react-router-dom';

import VoteGenerator from '@/pages/VoteGenerator';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Vote/VoteGenerator',
	component: VoteGenerator,
	decorators: [
		(Story) => (
			<BrowserRouter>
				<div>
					<Story />
				</div>
			</BrowserRouter>
		),
	],
} as Meta;

const Template: Story = (args) => <VoteGenerator {...args} />;

export const DefaultVoteGenerator = Template.bind({});
DefaultVoteGenerator.args = {};
