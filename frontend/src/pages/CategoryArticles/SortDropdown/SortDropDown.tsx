import { useState } from 'react';
import * as S from '@/pages/CategoryArticles/SortDropdown/SortDropdown.styles';

const SortList = ['최신순', '조회순'];

interface SortPropDownProps {
	sortIndex: string;
	setSortIndex: React.Dispatch<React.SetStateAction<string>>;
}

const SortDropdown = ({ sortIndex, setSortIndex }: SortPropDownProps) => {
	const [onDropdown, setOnDropdown] = useState(false);

	const onClickDropdownItem = (sort: string) => {
		setSortIndex(sort);
		setOnDropdown(false);
	};

	return (
		<S.Container>
			<S.SortBox
				onClick={() => {
					setOnDropdown((prevOnDropdown) => !prevOnDropdown);
				}}
			>
				<div>{sortIndex}</div>
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
