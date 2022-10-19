import { BrowserRouter } from 'react-router-dom';

import MenuSlider from '@/components/@common/MenuSlider/MenuSlider';
import { INITIAL_VIEWPORTS } from '@storybook/addon-viewport';
import { Meta, Story } from '@storybook/react';

const modalRoot = document.createElement('div');
modalRoot.setAttribute('id', 'menu-slider');
document.body.append(modalRoot);

export default {
	title: 'Layout/MenuSlider',
	component: MenuSlider,
	decorators: [
		(Story) => (
			<BrowserRouter>
				<Story />
			</BrowserRouter>
		),
	],
	parameters: {
		viewport: {
			viewports: INITIAL_VIEWPORTS,
			defaultViewport: 'iphone6',
		},
	},
} as Meta;

const Template: Story = (args) => <MenuSlider {...args} />;

export const DefaultMenuSlider = Template.bind({});
DefaultMenuSlider.args = {};
