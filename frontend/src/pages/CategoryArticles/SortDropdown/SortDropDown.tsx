import { useState } from 'react';
import * as S from '@/pages/CategoryArticles/SortDropdown/SortDropdown.styles';

const SortList = ['최신순', '추천순', '조회순'];

const SortDropdown = () => {
	const [onDropdown, setOnDropdown] = useState(false);
	const [selectedOption, setSelectedOption] = useState('최신순');

	const onClickDropdownItem = (sort: string) => {
		setSelectedOption(sort);
		setOnDropdown(false);
	};

	return (
		<S.Container>
			<S.SortBox
				onClick={() => {
					setOnDropdown((prevOnDropdown) => !prevOnDropdown);
				}}
			>
				<div>{selectedOption}</div>
				<S.ArrowDown />
			</S.SortBox>

			{onDropdown && (
				<S.DropdownBox>
					{SortList.map((sort, idx) => (
						<S.DropdownItem key={idx} idx={idx} onClick={() => onClickDropdownItem(sort)}>
							{sort}
						</S.DropdownItem>
					))}
				</S.DropdownBox>
			)}
		</S.Container>
	);
};

export default SortDropdown;
